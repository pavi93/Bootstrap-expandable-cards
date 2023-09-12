import java.io.File;
import java.io.IOException;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

public class SetFilePermissions {
    public static void main(String[] args) {
        String filePath = "example.txt";

        // Define the desired permissions (e.g., read, write, execute for owner)
        String permissions = "rw-r--r--";

        try {
            // Create a File object for the file
            File file = new File(filePath);

            // Get the PosixFileAttributeView to set permissions (works on Unix-like systems)
            PosixFileAttributeView posixView = file.toPath().getFileAttributeView(PosixFileAttributeView.class);
            
            if (posixView != null) {
                Set<PosixFilePermission> posixPermissions = PosixFilePermissions.fromString(permissions);
                posixView.setPermissions(posixPermissions);
                System.out.println("File permissions set successfully.");
            } else {
                System.out.println("PosixFileAttributeView is not supported on this system.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


