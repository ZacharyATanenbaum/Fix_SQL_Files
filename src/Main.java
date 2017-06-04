/**
 * A program to convert all .sql files in the current folder from gatherer extractor to be interpreted by the mysql database.
 * @auther Zach Tanenbaum
 */

import java.io.*;

public class Main {

    public static void main (String[] args) {

        // Input path of text documents
        final String INPUT_PATH = "./";

        // Get List of .lss files
        File[] files = finder(INPUT_PATH);

        // State number of files to change
        System.out.println("Number of .lss Files to Change: "+files.length);

        // Increment through each file and write to new file without spacing in front of lines with ';' and '.'
        // at the start.
        for (File file: files) {

            System.out.println("Changing "+file.getName());

            // Create new file to write
            File newFile = new File("fixed_"+file.getName());

            // Create new out BufferWriter
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter(newFile));
                System.out.println("Started BufferedWriter for "+newFile);

                // Create Buggered Reader to read input file
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    System.out.println("Started BufferedWriter for "+file.getName());

                    // Read all of input file and write to output file
                    try {
                        String line;
                        while ((line = br.readLine()) != null) {

                            // Start going through line
                            for (int i=0; i<line.length(); i++) {

                                // Check if character is a space or a ; or a .
                                if (line.charAt(i) != ' ' && line.charAt(i) != ';' && line.charAt(i) != '.') {
                                    // If the first character is not a ;, break and write the line
                                    out.write(line);
                                    break;
                                } else {
                                    // If it is a ; or ., output the line without the beginning space
                                    if (line.charAt(i) == ';' || line.charAt(i) == '.') {
                                        out.write(line.substring(i, line.length()-1));
                                        break;
                                    }
                                }
                            }

                            // Create new line
                            out.newLine();

                        }
                        // Close Original and Fixed Files
                        out.close();
                        br.close();
                    } catch (IOException e) {
                        System.out.println("Error processing input file:"+e);
                    }
                } catch (IOException e) {
                    System.out.println("Error in reading a file: "+e);
                }
            } catch (IOException e) {
                System.out.println("Problem Writing to New Fixed File, Error: "+e);
            }
        }
    }


    /**
     * Method to find all files in the dirName of the provided file type .lss
     * @param dirName The directory to check
     * @return an array of files in the directory with the .lss extension
     */
    public static File[] finder( String dirName){
        File dir = new File(dirName);

        return dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename)
            { return filename.endsWith(".lss"); }
        } );
    }

}
