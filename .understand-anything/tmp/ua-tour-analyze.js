const fs = require('fs');
const path = require('path');

function main() {
    const [,, inputPath, outputPath] = process.argv;
    const input = JSON.parse(fs.readFileSync(inputPath, 'utf8'));
    const { nodes, edges, layers } = input;

    // A. Fan-In Ranking
    const fanIn = {};
    const fanOut = {};
    edges.forEach(edge => {
        fanIn[edge.target] = (fanIn[edge.target] || 0) + 1;
        fanOut[edge.source] = (fanOut[edge.source] || 0) + 1;
    });

    const fanInRanking = nodes.map(n => ({ id: n.id, fanIn: fanIn[n.id] || 0, name: n.name }))
        .sort((a, b) => b.fanIn - a.fanIn).slice(0, 20);

    const fanOutRanking = nodes.map(n => ({ id: n.id, fanOut: fanOut[n.id] || 0, name: n.name }))
        .sort((a, b) => b.fanOut - a.fanOut).slice(0, 20);

    // C. Entry Point Candidates
    const entryPointCandidates = nodes.map(node => {
        let score = 0;
        if (node.id === 'document:README.md') score += 5;
        if (node.name === 'Game.java') score += 7;
        if (node.type === 'document') score += 2;
        return { id: node.id, score, name: node.name, summary: node.summary };
    }).sort((a, b) => b.score - a.score).slice(0, 5);

    // D. BFS Traversal
    const startCode = 'file:src/main/java/com/cousersoft/game/Game.java';
    const queue = [startCode];
    const visited = new Set([startCode]);
    const order = [];
    const depthMap = { [startCode]: 0 };
    const byDepth = { 0: [startCode] };

    while (queue.length > 0) {
        const current = queue.shift();
        order.push(current);
        const depth = depthMap[current];

        edges.filter(e => e.source === current).forEach(edge => {
            if (!visited.has(edge.target)) {
                visited.add(edge.target);
                queue.push(edge.target);
                depthMap[edge.target] = depth + 1;
                if (!byDepth[depth + 1]) byDepth[depth + 1] = [];
                byDepth[depth + 1].push(edge.target);
            }
        });
    }

    // E. Non-Code Inventory
    const nonCodeFiles = {
        documentation: nodes.filter(n => n.type === 'document'),
        infrastructure: nodes.filter(n => ['service', 'pipeline', 'resource'].includes(n.type)),
        data: nodes.filter(n => ['table', 'schema', 'endpoint'].includes(n.type) || (n.type === 'file' && n.name.endsWith('.tmx'))),
        config: nodes.filter(n => n.type === 'config')
    };

    // Final Assembly
    const results = {
        scriptCompleted: true,
        entryPointCandidates,
        fanInRanking,
        fanOutRanking,
        bfsTraversal: { startNode: startCode, order, depthMap, byDepth },
        nonCodeFiles,
        clusters: [],
        layers: { count: layers.length, list: layers },
        nodeSummaryIndex: Object.fromEntries(nodes.map(n => [n.id, { name: n.name, type: n.type, summary: n.summary }])),
        totalNodes: nodes.length,
        totalEdges: edges.length
    };

    fs.writeFileSync(outputPath, JSON.stringify(results, null, 2));
}

main();
