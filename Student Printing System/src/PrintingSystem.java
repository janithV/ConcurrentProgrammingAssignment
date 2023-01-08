import java.io.PrintWriter;

public class PrintingSystem {
    public static void main(String[] args) {

        ThreadGroup studentGroup = new ThreadGroup("Students");
        ThreadGroup technicianGroup = new ThreadGroup("Technician");

        ServicePrinter sharedPrinter = new LaserPrinter("IIT Printer", 1,50, studentGroup);

        Thread student1 = new Student(studentGroup,"student1", sharedPrinter);
        Thread student2 = new Student(studentGroup,"student2", sharedPrinter);
        Thread student3 = new Student(studentGroup,"student3", sharedPrinter);
        Thread student4 = new Student(studentGroup,"student4", sharedPrinter);

        Thread paperTechnician = new PaperTechnician(technicianGroup,"paperTechnician", sharedPrinter);
        Thread tonerTechnician = new TonerTechnician(technicianGroup, "tonerTechnician",sharedPrinter);

        student1.start();
        student2.start();
        student3.start();
        student4.start();

        paperTechnician.start();
        tonerTechnician.start();


        try {
            student1.join();
            student2.join();
            student3.join();
            student4.join();
            paperTechnician.join();
            tonerTechnician.join();
        }
        catch (InterruptedException e){
            System.out.println(e.getMessage());
        }

        System.out.println("-----------------" + TextColor.GREEN_TEXT + "PRINTER EXECUTION FINISHED" + TextColor.RESET + "-----------------");
        System.out.println(sharedPrinter);

    }
}