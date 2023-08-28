import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JGitCloneAndUpdateSingleFile {

    public static void main(String[] args) throws IOException, GitAPIException {
        // URL of the Bitbucket repository
        String repositoryUrl = "https://bitbucket.org/owner/repo.git"; // Replace with your repository URL

        // Bitbucket credentials
        String username = "your_username";
        String password = "your_password";

        // Path to the temporary directory
        Path tempDirectory = Files.createTempDirectory("jgit_temp");

        // Clone the repository into the temporary directory
        CloneCommand cloneCommand = Git.cloneRepository()
                .setURI(repositoryUrl)
                .setDirectory(tempDirectory.toFile())
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password));

        try (Git git = cloneCommand.call()) {
            // Specify the path of the file you want to update
            String filePath = "path/to/your/file.txt"; // Replace with the actual file path

            // Update the file with new content
            String newContent = "Updated content goes here.";
            Path updatedFilePath = tempDirectory.resolve(filePath);
            Files.write(updatedFilePath, newContent.getBytes());

            // Stage and commit the changes
            git.add().addFilepattern(filePath).call();
            git.commit().setMessage("Updated file").call();

            // Push the changes to the remote repository
            CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(username, password);
            git.push().setCredentialsProvider(credentialsProvider).call();
        } finally {
            // Clean up: Delete the temporary directory
            Files.walk(tempDirectory)
                    .sorted((a, b) -> -a.compareTo(b))
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }
}
