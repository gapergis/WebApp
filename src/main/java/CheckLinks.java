import java.util.ArrayList;
import java.util.List;

public class CheckLinks {

    private final ArrayList<Service> services;
    private final List<Useful> usefuls;
    int green = 0;
    int yellow = 0;
    int red = 0;
    int invalid = 0;
    int total = 0;

    final CheckController chController=new CheckController();
    Controller controller=new Controller();

    public CheckLinks(ArrayList<Service> services, List<Useful> usefuls) {

        this.services = services;
        this.usefuls = usefuls;
    }

    
}