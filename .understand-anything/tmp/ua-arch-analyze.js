const fs = require('fs');
const path = require('path');

function main() {
    const [,, inputPath, outputPath] = process.argv;
    const input = JSON.parse(fs.readFileSync(inputPath, 'utf8'));
    const { fileNodes, importEdges, allEdges } = input;

    // A. Directory Grouping
    const commonPrefix = findCommonPrefix(fileNodes.map(n => n.filePath));
    const directoryGroups = {};
    fileNodes.forEach(node => {
        let relPath = node.filePath.substring(commonPrefix.length);
        let group = relPath.split(/[/\\]/)[0] || 'root';
        if (!directoryGroups[group]) directoryGroups[group] = [];
        directoryGroups[group].push(node.id);
    });

    // B. Node Type Grouping
    const nodeTypeGroups = {};
    fileNodes.forEach(node => {
        if (!nodeTypeGroups[node.type]) nodeTypeGroups[node.type] = [];
        nodeTypeGroups[node.type].push(node.id);
    });

    // C. Import Adjacency Matrix & Fan-in/out
    const fileFanIn = {};
    const fileFanOut = {};
    const interGroupImports = [];
    const intraGroupDensity = {};

    // Initialize metrics
    Object.keys(directoryGroups).forEach(g => {
        intraGroupDensity[g] = { internalEdges: 0, totalEdges: 0, density: 0 };
    });

    importEdges.forEach(edge => {
        fileFanOut[edge.source] = (fileFanOut[edge.source] || 0) + 1;
        fileFanIn[edge.target] = (fileFanIn[edge.target] || 0) + 1;

        const sourceGroup = getGroup(edge.source, directoryGroups);
        const targetGroup = getGroup(edge.target, directoryGroups);

        if (sourceGroup && targetGroup) {
            if (sourceGroup === targetGroup) {
                intraGroupDensity[sourceGroup].internalEdges++;
            } else {
                let existing = interGroupImports.find(i => i.from === sourceGroup && i.to === targetGroup);
                if (existing) existing.count++;
                else interGroupImports.push({ from: sourceGroup, to: targetGroup, count: 1 });
            }
            intraGroupDensity[sourceGroup].totalEdges++;
            intraGroupDensity[targetGroup].totalEdges++;
        }
    });

    Object.keys(intraGroupDensity).forEach(g => {
        const stats = intraGroupDensity[g];
        if (stats.totalEdges > 0) stats.density = stats.internalEdges / stats.totalEdges;
    });

    // D. Cross-Category Dependency Analysis
    const crossCategoryEdges = [];
    allEdges.forEach(edge => {
        const sourceNode = fileNodes.find(n => n.id === edge.source);
        const targetNode = fileNodes.find(n => n.id === edge.target);
        if (sourceNode && targetNode && sourceNode.type !== targetNode.type) {
            let existing = crossCategoryEdges.find(e => e.fromType === sourceNode.type && e.toType === targetNode.type && e.edgeType === edge.type);
            if (existing) existing.count++;
            else crossCategoryEdges.push({ fromType: sourceNode.type, toType: targetNode.type, edgeType: edge.type, count: 1 });
        }
    });

    // G. Pattern Matching (simplified)
    const patternMatches = {};
    Object.keys(directoryGroups).forEach(g => {
        if (['src', 'main', 'java'].includes(g.toLowerCase())) patternMatches[g] = 'service';
        else if (['graphics', 'images'].includes(g.toLowerCase())) patternMatches[g] = 'ui';
        else if (['simulation'].includes(g.toLowerCase())) patternMatches[g] = 'service';
        else if (['input'].includes(g.toLowerCase())) patternMatches[g] = 'middleware';
        else if (['root'].includes(g.toLowerCase())) patternMatches[g] = 'config';
    });

    // Final Assembly
    const results = {
        scriptCompleted: true,
        directoryGroups,
        nodeTypeGroups,
        crossCategoryEdges,
        interGroupImports,
        intraGroupDensity,
        patternMatches,
        fileStats: {
            totalFileNodes: fileNodes.length,
            nodeTypeCounts: Object.fromEntries(Object.entries(nodeTypeGroups).map(([k, v]) => [k, v.length]))
        },
        fileFanIn,
        fileFanOut,
        dependencyDirection: interGroupImports.map(i => ({ dependent: i.from, dependsOn: i.to }))
    };

    fs.writeFileSync(outputPath, JSON.stringify(results, null, 2));
}

function findCommonPrefix(paths) {
    if (!paths.length) return '';
    let prefix = paths[0];
    for (let i = 1; i < paths.length; i++) {
        while (paths[i].indexOf(prefix) !== 0) {
            prefix = prefix.substring(0, prefix.length - 1);
            if (!prefix) break;
        }
    }
    // Ensure it ends with a separator
    if (prefix && !prefix.endsWith('/') && !prefix.endsWith('\\')) {
        const lastSlash = Math.max(prefix.lastIndexOf('/'), prefix.lastIndexOf('\\'));
        if (lastSlash !== -1) prefix = prefix.substring(0, lastSlash + 1);
    }
    return prefix;
}

function getGroup(nodeId, groups) {
    for (const [group, nodes] of Object.entries(groups)) {
        if (nodes.includes(nodeId)) return group;
    }
    return null;
}

main();
