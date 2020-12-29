

public class Classroom{
  private String name = "";
  private String students= "";
  private int num_students;
  private final int MAX_STUDENTS = 30; 

  public Classroom(String theName){
    name = theName;
  }

  public String getName(){
    return name;
  }

  public void setName(String theName){
    name = theName;
  }

  public int getNumStudents(){
    return num_students; 
  }

  public void addStudent(String theStudent){
    if(num_students<MAX_STUDENTS){
      students = students + " " + theStudent;
      num_students++;
      students = students.trim();
    }
  }
 
 public String toString(){
   return students;
 }

public boolean removeStudent(String theName){
  int studentIndex = students.indexOf(theName);

  if(studentIndex == -1)
    return false;
  else{
    int len = theName.length();
    int spaceIndex = studentIndex + len;
    if(spaceIndex < students.length() &&  students.substring(spaceIndex, spaceIndex+1).equals(" "))
      len++;

    students = students.substring(0,studentIndex)+students.substring(studentIndex+len);
    students = students.trim();
    num_students--;
    return true;

  }
  
}
 







}
