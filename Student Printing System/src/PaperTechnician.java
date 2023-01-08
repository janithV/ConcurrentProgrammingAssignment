import java.util.Random;

public class PaperTechnician extends Thread{

    private ServicePrinter printer;

    public PaperTechnician(ThreadGroup group, String name, ServicePrinter printer) {
        super(group, name);
        this.printer = printer;
    }

    public Printer getPrinter() {
        return printer;
    }

    @Override
    public void run(){
        Random random = new Random();

        for (int i = 1 ; i<=3; i++){
            printer.refillPaper();

            int sleepTime = random.nextInt(5000 - 1000) + 1000;
            try {
                Thread.sleep(sleepTime);
            }
            catch (InterruptedException e){
                System.out.println(e.getMessage());
            }
        }

        System.out.println(TextColor.PURPLE_TEXT + "[PAPER TECHNICIAN]" +  TextColor.GREEN_TEXT + " - Paper Technician Finished Execution"
                + TextColor.RESET );

    }
}
