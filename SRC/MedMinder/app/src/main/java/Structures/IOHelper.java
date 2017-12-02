package Structures;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Mahad Khokhar on 2017-11-28.
 */


/**<h1>This java file is used for the assistance of </h1>
 *
 * @author Mahad Khokhar
 * Date: Nov 28 2017
 * @version 0
 *
 *<p> The class will allow the user to read and write to files which are stored internally. The
 * file will be delete the user will delete the application. Only the current application has access to it </p>
 */
public class IOHelper {

    //Extension for reading the data
    private static String stringFromStream(InputStream is) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null)
                sb.append(line).append("\n");
            reader.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Getting the String
     * @param f the name of the file
     * @return the string in the file
     * @throws IOException
     */
    public static String stringFromFile(File f) throws IOException {
        FileInputStream fis = new FileInputStream(f);
        String str = stringFromStream(fis);
        fis.close();
        return str;
    }

    /**
     *  This function will write to the file
     * @param f the name of the file
     * @param str the string you want to string to
     * @throws IOException
     */
    public static void writeToFile(File f, String str) throws IOException {
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(str.getBytes());
        fos.close();

    }

    /**
     * Used to write to the file
     * @param context the context so you can reference the file
     * @param fileName the name of the file
     * @param str The string you want to write
     */
    public static void writeToFile(Context context, String fileName, String str) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(str.getBytes(), 0, str.length());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will read from the file
     * @param context the context to reference the file
     * @param fileName the name of the file
     * @return the string in the file
     */
    public static String read (Context context, String fileName) {
        try {
            InputStream is = context.openFileInput(fileName);
            String result = IOHelper.stringFromStream(is);
            is.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method is used for creating a file if it doesn't exist
     * @param context get reference to the directory
     * @param fileName the name of the file you want to create
     */
    public static void create (Context context, String fileName) {

        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(fileName, Context.MODE_APPEND);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
