import java.util.Random;

public class TonerTechnician extends Thread{

    private ServicePrinter printer;

    public TonerTechnician(ThreadGroup group, String name, ServicePrinter printer) {
        super(group, name);
        this.printer = printer;
    }

    public ServicePrinter getPrinter() {
        return printer;
    }

    @Override
    public void run(){
        Random random = new Random();

        for (int i = 1; i<=3 ; i++){
            printer.replaceTonerCartridge();

            int sleepTime = random.nextInt(5000 - 1000) + 1000;
            try {
                Thread.sleep(sleepTime);
            }
            catch (InterruptedException e){
                System.out.println(e.getMessage());
            }
        }

        System.out.println(TextColor.YELLOW_TEXT + "[TONER TECHNICIAN]" +  TextColor.GREEN_TEXT + " - Toner Technician Finished Execution" + TextColor.RESET);

    }
}
