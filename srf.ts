const http = require('http');
const https = require('https');
const readline = require('readline');

// Create a readline interface for user input
const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout
});

// Ask the user to enter a URL
rl.question('Please enter a URL:\n', (userInput) => {
  // Close the readline interface
  rl.close();
  
  try {
    // Parse the user input to a URL object
    const url = new URL(userInput);
    
    // Choose the appropriate protocol
    const protocol = url.protocol === 'https:' ? https : http;
    
    // Make a GET request to the specified URL
    protocol.get(url, (res) => {
      let data = '';
      
      // Concatenate each chunk of data
      res.on('data', (chunk) => {
        data += chunk;
      });
      
      // Log the entire data once the response is ended
      res.on('end', () => {
        console.log(data);
      });
    }).on('error', (err) => {
      // Handle errors that occur while making the request
      console.error('Error:', err.message);
    });
  } catch (err) {
    // Handle errors that occur while parsing the URL
    console.error('Invalid URL:', err.message);
  }
});
