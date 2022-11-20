
import java.util.ArrayList;

public class Data {

    ArrayList<Ministry> mins;
    ArrayList<Organization> orgs;
    ArrayList<Category> cats;
    ArrayList<Subcategory> subs;
    ArrayList<Service> services;
    ArrayList<Useful> usefuls;


    public Data(ArrayList<Ministry> mins, ArrayList<Organization> orgs, ArrayList<Category> cats, ArrayList<Subcategory> subs, ArrayList<Service> services, ArrayList<Useful> usefuls) {
        this.mins = mins;
        this.orgs = orgs;
        this.cats = cats;
        this.subs = subs;
        this.services = services;
        this.usefuls = usefuls;
    }
}
