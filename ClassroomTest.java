import java.lang.reflect.*;
/**
 * This class tests the Classroom and DiagonalTest classes
 * 
 * @author Mr. Aronson
 */
public class ClassroomTest
{
    String className = "Classroom";
    private  boolean failed = false;
    private  Object t1, t2, t3;
    private  Class<?> c, c2;
    Object[] cArgs = {"Test"};
    Object[] cArgs2 = {};
    private Constructor constructor, constructor2;

    public static void main(String[] args)
    {
        ClassroomTest c = new ClassroomTest();
        c.doTest();
    }

    public void doTest() {
        //********** Classroom Class Test **************************************
        // Instantiate a new Classroom object
        System.out.println("Testing your Classroom class \n");
        System.out.println("Now testing instance variables and constructor");
        try
        {

            c = Class.forName(className);
            constructor = c.getConstructor(new Class[] {String.class});
            t1 = constructor.newInstance(cArgs);
        }
        catch (NoClassDefFoundError e)
        {
            failure("Epic Failure: missing Classroom class");
        }
        catch (ClassNotFoundException e)
        {
            failure("Epic Failure: missing Classroom class");
        }
        catch (NoSuchMethodException e)
        {
            failure("missing constructor Classroom(String theName)");
        }
        catch (Exception e) {failure(e.toString());}

        int numDoubleFields = 0;
        int numIntFields = 1;
        boolean num_students_set = false;
        boolean maxStudents_set = false;
        boolean name_set = false;
        boolean students_set = false;
        if(!failed)
        {
            Field[] fields = c.getDeclaredFields();
            if(fields.length == 0)
                failure("Classroom has no instance variables");
            else
            {
                for(Field field : fields)
                {
                    //   System.out.println(field);
                    String temp = field.toString();

                    if (temp.contains("final") && temp.contains("int") && temp.contains("MAX_STUDENTS"))
                    {

                        try
                        {
                            field.setAccessible(true);
                            if ((int)field.getInt(t1) == 30)
                                maxStudents_set = true;
                            else
                                failure("MAX_STUDENTS is not 30");
                        }
                        catch (IllegalAccessException e)
                        {
                            failure("illegal access exception");
                        }
                    } else  if (temp.contains("private") && temp.contains("int") && temp.contains("num_students"))
                    {

                        try
                        {
                            field.setAccessible(true);
                            if ((int)field.getInt(t1) == 0)
                                num_students_set = true;
                            else
                                failure("num_students is not 0");
                        }
                        catch (IllegalAccessException e)
                        {
                            failure("illegal access exception");
                        }

                    } else  if (temp.contains("private") && temp.contains("String") && temp.contains("name"))
                    {
                        name_set = true;

                    } else  if (temp.contains("private") && temp.contains("String") && temp.contains("students"))
                    {
                        students_set = true;
                    }
                }
            }
        }

        if(!failed && !num_students_set)
            failure("Classroom instance variable num_students not created properly");
        if(!failed && !name_set)
            failure("Classroom instance variable name not created properly");
        if(!failed && !students_set)
            failure("Classroom instance variable students not created properly");
        if(!failed && !maxStudents_set)
            failure("Classroom variable MAX_STUDENTS needs to be final and set to 30");
        if (!failed && !callGet(t1, "getName").equals("Test"))
            failure("Classroom constructor not setting name properly");

        if(!failed)
            System.out.println("Passed instance variables and constructor\n");

        if(!failed)
        {
            System.out.println("Now testing getNumStudents");
            int num = (int)callGet(t1, "getNumStudents");
            if(num!= 0)

                failure("num students should be 0 at the beginning");

        }
        if(!failed)
            System.out.println("Passed getNumStudents\n");

        // Test setTime method
        if(!failed)
        {
            System.out.println("Now testing getName and setName");

            callSet(t1, "setName", String.class, "Test2");

            if (!callGet(t1, "getName").equals("Test2"))
                failure("setName not working properly");

        }

        if(!failed)
            System.out.println("Passed getName and setName\n");

        if(!failed)
        {
            System.out.println("Now testing addStudent");

            callSet(t1, "addStudent", String.class, "s1");
            callSet(t1, "addStudent", String.class, "s2");
            callSet(t1, "addStudent", String.class, "s3");
        }
        if (!failed)
            System.out.println("Passed addStudent\n");

        if(!failed)
        {
            // Test getNumStudents method
            System.out.println("Now testing getNumStudents");

            int num = (int) callGet(t1, "getNumStudents");
            if(num != 3)
                failure("num students is" + num + ", should be 3");
        }
        if (!failed)
            System.out.println("Passed getNumStudents\n");

        if(!failed)
        {
            System.out.println("Now testing if addStudent error checks for too many");
            try
            {
                cArgs[0] = "Test2";
                t2 = constructor.newInstance(cArgs);

                for (int i = 0; i < 30; i++)
                    callSet(t2, "addStudent", String.class, "" + i);
                callSet(t2, "addStudent", String.class, "Should fail");

                if ((int)callGet(t2, "getNumStudents") > 30)
                    failure("addStudent allows too many students to be created. ");
            }
            catch (Exception e)
            {
                failure("addStudent allows too many students to be created.  Return false if too many, true otherwise.");
            }
        }
        if (!failed)
            System.out.println("Passed addStudent error checks\n");

        if(!failed)
        {
            System.out.println("Now testing toString");
            String objectToString = t1.getClass().getName() + '@' + Integer.toHexString(System.identityHashCode(t1));
            if(t1.toString().equals(objectToString))
                failure("missing toString");
        }

        if(!failed)
        {
            if(!t1.toString().equals("s1 s2 s3"))
                failure("" + t1.toString() + " is an invalid toString format");
        }

        if(!failed)
            System.out.println("Passed toString\n");

        // Test diagonal method
        if(!failed)
        {
            System.out.println("Now testing addStudents (part 2)");
            callSet(t1, "addStudent", String.class, "s4");
            if (!t1.toString().equals("s1 s2 s3 s4"))
                failure("something not working properly");

        }
        if(!failed)
            System.out.println("Passed addStudents (part 2)\n");

        if (!failed)
        {
            System.out.println("Now testing your Sample class: \n");
        }

        if (!failed)
        {
            try
            {

                c2 = Class.forName("Sample");
                constructor2 = c2.getConstructor(new Class[] {});
                t3 = constructor2.newInstance(cArgs2);
            }
            catch (NoClassDefFoundError e)
            {
                failure("Epic Failure: missing Sample class");
            }
            catch (ClassNotFoundException e)
            {
                failure("Epic Failure: missing Sample class");
            }
            catch (Exception e) {failure(e.toString());}
        }

        if(!failed)
        {
            System.out.println("Now testing create");
            try
            {

                Method m = c2.getDeclaredMethod("create");
                Object obj = m.invoke(t3);

                String str = obj.toString();
                if (!str.equals("Sam Sara Nate"))
                    failure("create method does not create Sam Sara Nate string properly");
            }
            catch (NoSuchMethodException e)
            {
                failure("missing create() method");
            }
            catch (Exception e) {failure(e.toString());}
        }
        if(!failed)
            System.out.println("Passed create\n");

        if(!failed)
        {
            System.out.println("Congratulations, your Sample class works correctly \n");
        }
        if(!failed)
        {
            System.out.println("Now testing removeStudent");

            if (!callRemove(t1, "s4"))
                failure("remove last student not working properly");
            if (!t1.toString().equals("s1 s2 s3"))
                failure("removeStudent not working properly returns \"" + t1.toString() + "\" but should be \"s1 s2 s3\"");
            if ((int)callGet(t1, "getNumStudents") != 3)
                failure("num_students should be 3 but is " + (int)callGet(t1, "getNumStudents"));
            if (!callRemove(t1, "s2"))
                failure("remove middle student not working properly");
            if (!t1.toString().equals("s1 s3"))
                failure("removeStudent not working properly returns \"" + t1.toString() + "\" but should be \"s1 s3\"");
            if ((int)callGet(t1, "getNumStudents") != 2 )
                failure("num_students should be 2 but is " + (int)callGet(t1, "getNumStudents"));
            if (!callRemove(t1, "s1"))
                failure("remove first student not working properly");
            if (!t1.toString().equals("s3"))
                failure("removeStudent not working properly returns \"" + t1.toString() + "\" but should be \"s3\"");
            if ((int)callGet(t1, "getNumStudents") != 1 )
                failure("num_students should be 1 but is " + (int)callGet(t1, "getNumStudents"));
            if (callRemove(t1, "Fred"))
                failure("Should not remove student that is not there.");
            if (!callRemove(t1, "s3"))
                failure("remove final student not working properly");
            if (!t1.toString().equals(""))
                failure("removeStudent not working properly returns \"" + t1.toString() + "\" but should be \"\"");
            if ((int)callGet(t1, "getNumStudents") != 0 )
                failure("num_students should be 0 but is " + (int)callGet(t1, "getNumStudents"));
        }
        if (!failed)
            System.out.println("Passed removeStudent\n");

        if(!failed)
            System.out.println("Yay! You have successfully completed the Classroom Project!");
        else
            System.out.println("\nBummer.  Try again.");
    }

    private void failure(String str)
    {
        System.out.println("*** Failed: " + str);
        failed = true;
    }

    public Object callGet(Object t, String get)
    {
        try
        {
            Method m = c.getDeclaredMethod(get);
            Object temp = m.invoke(t);
            return temp;
        }
        catch (NoSuchMethodException e)
        {
            failure("missing get method");
            System.exit(0);
        }
        catch(Exception e) {
            failure(e.toString());
            System.exit(0);}
        return null;
    }

    public void callSet(Object t, String setMethod, Class cl, Object setValue)
    {
        try
        {

            Method m = c.getDeclaredMethod(setMethod, cl);
            m.invoke(t, setValue);
        }
        catch (NoSuchMethodException e)
        {
            failure("missing set method");
            System.exit(0);
        }
        catch(Exception e) {
            failure(e.toString());
            System.exit(0);
        }

    }

    public boolean callRemove(Object t, String value)
    {
        try
        {

            Method m = c.getDeclaredMethod("removeStudent", String.class);
            Object obj = m.invoke(t, value);
            return (boolean)obj;
        }
        catch (NoSuchMethodException e)
        {
            failure("missing removeStudent method");
            System.exit(0);
        }
        catch(Exception e) {
            failure(e.toString());
            System.exit(0);}
        return false;
    }

}
