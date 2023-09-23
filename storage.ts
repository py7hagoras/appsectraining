const express = require('express');
const multer = require('multer');
const path = require('path');
const fs = require('fs');

const app = express();
const PORT = 3000;
const UPLOAD_PATH = path.join(__dirname, 'uploads');

// Create the uploads directory if it doesn't exist
if (!fs.existsSync(UPLOAD_PATH)) {
    fs.mkdirSync(UPLOAD_PATH);
}

// Configure storage for Multer
const storage = multer.diskStorage({
    destination: (req, file, cb) => {
        // Set the directory for storing uploaded files
        cb(null, UPLOAD_PATH);
    },
    filename: (req, file, cb) => {
        // Save the file with a date-time prefix
        cb(null, new Date().toISOString().replace(/:/g, '-') + path.extname(file.originalname));
    }
});

const upload = multer({ storage: storage });

// Route for handling file uploads
app.post('/upload', upload.single('file'), (req, res) => {
    res.send('File uploaded successfully!');
});

// Route for handling file downloads
app.get('/download/:filename', (req, res) => {
    const filePath = path.join(UPLOAD_PATH, req.params.filename);
    res.download(filePath);
});

// Start the server
app.listen(PORT, () => {
    console.log(`Server started on http://localhost:${PORT}`);
});
