import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.DosFileAttributes;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SetFilePermissions {
    public static void main(String[] args) {
        String filePath = "example.txt";

        // Define the desired permissions (e.g., read, write, execute for owner)
        Set<PosixFilePermission> permissions = new HashSet<>();
        permissions.add(PosixFilePermission.OWNER_READ);
        permissions.add(PosixFilePermission.OWNER_WRITE);
        permissions.add(PosixFilePermission.OWNER_EXECUTE);

        try {
            // Get the file's path
            Path file = Path.of(filePath);

            // Set the permissions
            Files.setPosixFilePermissions(file, permissions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
