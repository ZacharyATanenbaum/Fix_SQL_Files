/**
 * A program to convert all .sql files in the current folder from gatherer extractor to be interpreted by the mysql database.
 * @auther Zach Tanenbaum
 */

import java.io.*;

public class Main {

    public static void main (String[] args) {

        // Input path of text documents
        final String INPUT_PATH = "./";

        // Get List of .sql files
        File[] files = finder(INPUT_PATH);

        // State number of files to change
        System.out.println("Number of .sql Files to Change: "+files.length);

        // Increment through each file and write to new file without spacing in front of lines with ';' and '.'
        // at the start.
        for (File file: files) {

            System.out.println("Changing "+file.getName());

            // Create new file to write
            File newFile = new File("fixed_"+file.getName());

            // Create new out BufferWriter
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter(newFile));

                // Create Buggered Reader to read input file
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));

                    // Read all of input file and write to output file
                    try {
                        String line;
                        int count = 0;
                        while ((line = br.readLine()) != null) {

                            // Check if line is the second line. If it is, change the last character from ',' to ';'
                            if (count == 1) {
                                line = line.substring(0, line.length()-1);
                                line += ";";
                            }

                            // Write line to fixed file
                            out.write(line);

                            // Create new line
                            out.newLine();

                            // Increment Count
                            count++;

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
     * Method to find all files in the dirName of the provided file type .sql
     * @param dirName The directory to check
     * @return an array of files in the directory with the .lss extension
     */
    public static File[] finder( String dirName){
        File dir = new File(dirName);

        return dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename)
            { return filename.endsWith(".sql"); }
        } );
    }

}
