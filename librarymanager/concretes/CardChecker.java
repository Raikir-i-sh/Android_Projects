package trying.librarymanager.concretes;;
public class CardChecker extends  Checker{

    @Override
    public boolean IFlost() {
        System.out.println("Pay fine for making new Card");
        return true;
    }



}