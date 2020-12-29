
public class Sample
{

    public  Classroom create()
    {
        Classroom c = new Classroom("Aronson");
        c.addStudent("Sam");
        c.addStudent("Sara");
        c.addStudent("Nate");
        return c;

    }
}
