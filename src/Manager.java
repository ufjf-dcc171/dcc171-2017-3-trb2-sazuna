import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Manager {

    static List<Mesa> mesas;

    public static boolean ready = false;

    public static void init(List<Mesa> mesas) {
        Manager.mesas = mesas;
    }

    public static void save() {
        if(!ready) return;
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("data.txt"), "utf-8"))) {
            for (Mesa m : mesas) {
                writer.write("begin MESA\n");
                writer.write(m.serialize() + "\n");
                writer.write("end MESA\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar o arquivo de persistência!");
            System.out.println(e.getMessage());
        }
    }

    public static List<Mesa> restore() {
        ArrayList<Mesa> ms = new ArrayList<>();
        try {
            String line;
            try{
                File f = new File("data.txt");
                f.createNewFile();
            }catch(IOException ioe){
                JOptionPane.showMessageDialog(null, "Nao foi possivel criar o arquivo!");
                System.exit(-1);
            }

            FileReader fileReader =
                    new FileReader("data.txt");

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            line = bufferedReader.readLine();
            while ((line != null) && (!line.equals("\n"))) {
                ArrayList<String> mesaData = new ArrayList<>();
                while (!(line = bufferedReader.readLine()).equals("end MESA")) {
                    mesaData.add(line);
                }
                ms.add(Mesa.deserialize(mesaData));
                line = bufferedReader.readLine();
            }

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

            // Always close files.
            bufferedReader.close();
        } catch(FileNotFoundException fnf){
            JOptionPane.showMessageDialog(null, "File not found. This shouldnt happen");
        } catch(IOException ioe){
            JOptionPane.showMessageDialog(null, "Erro ao ler arquivo!");
        }
        Manager.mesas = ms;
        return ms;
    }
}
