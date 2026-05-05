const fs = require('fs');
const path = require('path');
const { execSync } = require('child_process');

const projectRoot = process.argv[2];
const outputPath = process.argv[3];

if (!projectRoot || !outputPath) {
    console.error('Usage: node ua-project-scan.js <projectRoot> <outputPath>');
    process.exit(1);
}

const ignorePatterns = [
    'node_modules', '.git', 'vendor', 'venv', '.venv', '__pycache__',
    'dist', 'build', 'out', 'coverage', '.next', '.cache', '.turbo', 'target', 'obj',
    '*.lock', 'package-lock.json', 'yarn.lock', 'pnpm-lock.yaml',
    '*.png', '*.jpg', '*.jpeg', '*.gif', '*.svg', '*.ico', '*.woff', '*.woff2', '*.ttf', '*.eot', '*.mp3', '*.mp4', '*.pdf', '*.zip', '*.tar', '*.gz',
    '*.min.js', '*.min.css', '*.map', '*.generated.*',
    '.idea', '.vscode',
    'LICENSE', '.gitignore', '.editorconfig', '.prettierrc', '.eslintrc*', '*.log'
];

function isIgnored(filePath) {
    const segments = filePath.split(path.sep);
    if (segments.some(s => ['node_modules', '.git', 'vendor', 'venv', '.venv', '__pycache__', 'dist', 'build', 'out', 'coverage', '.next', '.cache', '.turbo', 'target', 'obj', '.idea', '.vscode'].includes(s))) {
        return true;
    }
    const ext = path.extname(filePath).toLowerCase();
    const basename = path.basename(filePath);
    
    if (['.class', '.lock', '.png', '.jpg', '.jpeg', '.gif', '.svg', '.ico', '.woff', '.woff2', '.ttf', '.eot', '.mp3', '.mp4', '.pdf', '.zip', '.tar', '.gz', '.map', '.log'].includes(ext)) return true;
    if (['package-lock.json', 'yarn.lock', 'pnpm-lock.yaml', 'LICENSE', '.gitignore', '.editorconfig', '.prettierrc'].includes(basename)) return true;
    if (basename.startsWith('.eslintrc')) return true;
    if (basename.includes('.min.')) return true;
    if (basename.includes('.generated.')) return true;

    return false;
}

function getFiles(dir, allFiles = []) {
    const files = fs.readdirSync(dir);
    for (const file of files) {
        const name = path.join(dir, file);
        if (fs.statSync(name).isDirectory()) {
            if (!isIgnored(name)) {
                getFiles(name, allFiles);
            }
        } else {
            if (!isIgnored(name)) {
                allFiles.push(path.relative(projectRoot, name));
            }
        }
    }
    return allFiles;
}

const langMap = {
    '.ts': 'typescript', '.tsx': 'typescript',
    '.js': 'javascript', '.jsx': 'javascript',
    '.py': 'python',
    '.go': 'go',
    '.rs': 'rust',
    '.java': 'java',
    '.rb': 'ruby',
    '.cpp': 'cpp', '.cc': 'cpp', '.cxx': 'cpp', '.h': 'cpp', '.hpp': 'cpp',
    '.c': 'c',
    '.cs': 'csharp',
    '.swift': 'swift',
    '.kt': 'kotlin',
    '.php': 'php',
    '.vue': 'vue',
    '.svelte': 'svelte',
    '.sh': 'shell', '.bash': 'shell',
    '.md': 'markdown', '.rst': 'markdown',
    '.yaml': 'yaml', '.yml': 'yaml',
    '.json': 'json',
    '.toml': 'toml',
    '.sql': 'sql',
    '.graphql': 'graphql', '.gql': 'graphql',
    '.proto': 'protobuf',
    '.tf': 'terraform', '.tfvars': 'terraform',
    '.html': 'html', '.htm': 'html',
    '.css': 'css', '.scss': 'css', '.sass': 'css', '.less': 'css',
    '.xml': 'xml',
    '.cfg': 'config', '.ini': 'config', '.env': 'config'
};

function getLanguage(filePath) {
    const ext = path.extname(filePath).toLowerCase();
    if (path.basename(filePath) === 'Dockerfile') return 'dockerfile';
    if (path.basename(filePath) === 'Makefile') return 'makefile';
    if (path.basename(filePath) === 'Jenkinsfile') return 'jenkinsfile';
    return langMap[ext] || 'unknown';
}

function getCategory(filePath, lang) {
    const basename = path.basename(filePath);
    const ext = path.extname(filePath).toLowerCase();
    
    if (['.md', '.rst', '.txt'].includes(ext) && basename !== 'LICENSE') return 'docs';
    if (['.yaml', '.yml', '.json', '.toml', '.xml', '.cfg', '.ini', '.env'].includes(ext) || ['tsconfig.json', 'package.json', 'pyproject.toml', 'Cargo.toml', 'go.mod', 'pom.xml'].includes(basename)) {
        if (['Dockerfile', 'docker-compose.yml', 'docker-compose.yaml', 'Makefile', 'Jenkinsfile', 'Procfile', 'Vagrantfile'].includes(basename) || filePath.includes('.github/workflows') || ext === '.tf') return 'infra';
        return 'config';
    }
    if (['Dockerfile', 'docker-compose.yml', 'docker-compose.yaml', 'Makefile', 'Jenkinsfile', 'Procfile', 'Vagrantfile'].includes(basename) || filePath.includes('.github/workflows') || ext === '.tf' || filePath.includes('/k8s/') || filePath.includes('/kubernetes/')) return 'infra';
    if (['.sql', '.graphql', '.gql', '.proto', '.prisma', '.csv'].includes(ext) || basename.endsWith('.schema.json')) return 'data';
    if (['.sh', '.bash', '.ps1', '.bat'].includes(ext)) return 'script';
    if (['.html', '.htm', '.css', '.scss', '.sass', '.less'].includes(ext)) return 'markup';
    
    return 'code';
}

function countLines(filePath) {
    try {
        const content = fs.readFileSync(path.join(projectRoot, filePath), 'utf8');
        return content.split('\n').length;
    } catch (e) {
        return 0;
    }
}

try {
    let files = [];
    try {
        const gitFiles = execSync('git ls-files', { cwd: projectRoot }).toString().split('\n').filter(f => f.trim());
        files = gitFiles.filter(f => !isIgnored(f));
    } catch (e) {
        files = getFiles(projectRoot);
    }

    const fileList = files.map(f => {
        const lang = getLanguage(f);
        return {
            path: f,
            language: lang,
            sizeLines: countLines(f),
            fileCategory: getCategory(f, lang)
        };
    }).sort((a, b) => a.path.localeCompare(b.path));

    const languages = [...new Set(fileList.map(f => f.language))].filter(l => l !== 'unknown').sort();
    
    // Framework detection
    const frameworks = [];
    if (fs.existsSync(path.join(projectRoot, 'pom.xml'))) frameworks.push('Maven');
    if (fs.existsSync(path.join(projectRoot, 'Dockerfile'))) frameworks.push('Docker');
    
    const pomContent = fs.existsSync(path.join(projectRoot, 'pom.xml')) ? fs.readFileSync(path.join(projectRoot, 'pom.xml'), 'utf8') : '';
    if (pomContent.includes('spring-boot')) frameworks.push('Spring Boot');

    const readmeHead = fs.existsSync(path.join(projectRoot, 'README.md')) ? fs.readFileSync(path.join(projectRoot, 'README.md'), 'utf8').split('\n').slice(0, 10).join('\n') : '';

    const result = {
        scriptCompleted: true,
        name: path.basename(projectRoot),
        rawDescription: "",
        readmeHead: readmeHead,
        languages: languages,
        frameworks: frameworks,
        files: fileList,
        totalFiles: fileList.length,
        filteredByIgnore: 0,
        estimatedComplexity: fileList.length > 500 ? 'very-large' : (fileList.length > 150 ? 'large' : (fileList.length > 30 ? 'moderate' : 'small')),
        importMap: {}
    };

    // Simple import resolution for Java (skip as per instructions, but let's do a basic one if needed)
    // Actually instructions say: "Java/Kotlin | Not resolvable by path — skip import resolution for these languages"
    fileList.forEach(f => {
        result.importMap[f.path] = [];
    });

    fs.writeFileSync(outputPath, JSON.stringify(result, null, 2));
    process.exit(0);
} catch (e) {
    console.error(e);
    process.exit(1);
}
