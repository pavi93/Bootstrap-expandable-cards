InputStream inputStream = YourClass.class.getResourceAsStream("/yourimage.jpg"); // Adjust the path to your image

if (inputStream != null) {
    File tempFile = File.createTempFile("tempfile", ".jpg"); // Use ".jpg" for JPEG images

    try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
    } finally {
        inputStream.close();
        tempFile.deleteOnExit(); // Mark the temp file for deletion on JVM exit
    }

    String filePath = tempFile.toURI().toString();
    // Now, 'filePath' contains the file URI, and you can use it in your application.
} else {
    // Handle the case where the image file is not found
}
