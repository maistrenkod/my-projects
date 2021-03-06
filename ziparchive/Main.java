import ru.ncedu.maistrenkodaria.ac.Ziparchive;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Ziparchive a = new Ziparchive();
        Ziparchive b = new Ziparchive();
        Scanner in = new Scanner(System.in);
        System.out.println("Please, write the name of the first folder");
        a.readnameoffolder(in);
        System.out.println("Please, write the name of the second folder");
        b.readnameoffolder(in);
        a.openandrewritetoarrays();
        b.openandrewritetoarrays();
        a.find1not2(b);
        b.find1not2(a);
        a.file(b);
    }
}
