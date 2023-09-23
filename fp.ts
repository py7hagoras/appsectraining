const express = require('express');
const fs = require('fs');
const path = require('path');

const app = express();
const PORT = 3000;

// Assuming the application wants to allow users to view certain files from a 'files' directory
const BASE_DIRECTORY = path.join(__dirname, 'safe');

app.get('/getFile', (req, res) => {
    
    const filePath = path.join(BASE_DIRECTORY, req.query.filename);

    // Send the file as response
    res.sendFile(filePath);
});

app.listen(PORT, () => {
    console.log(`Server started on http://localhost:${PORT}`);
});
