import { spawn } from 'child_process';
import path from 'path';
import { fileURLToPath } from 'url';

const scriptPath = path.join(process.env.USERPROFILE, '.gemini', 'antigravity', 'skills', 'understand-anything', 'understand', 'extract-structure.mjs');
const scriptUrl = 'file:///' + scriptPath.replace(/\\/g, '/');

// We can't easily import and run because the script might use process.argv
// So we'll spawn another node process but with the file:// URL
const args = process.argv.slice(2);
const child = spawn('node', [scriptUrl, ...args], { stdio: 'inherit' });
child.on('exit', (code) => process.exit(code));
