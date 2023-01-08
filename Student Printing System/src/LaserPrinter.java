import java.io.PrintWriter;

public class LaserPrinter implements ServicePrinter{

    private String printerName;
    private int paperLevel;
    private int tonerLevel;
    private ThreadGroup students;

    private int packsFilled;

    private int cartridgesReplaced;

    public LaserPrinter(String printerName, int paperLevel, int tonerLevel, ThreadGroup students) {
        this.printerName = printerName;
        this.paperLevel = paperLevel;
        this.tonerLevel = tonerLevel;
        this.students = students;
        this.packsFilled = 0;
        this. cartridgesReplaced = 0;
    }

    @Override
    public synchronized void printDocument(Document document) {
        System.out.println("------------------------------------------------------------------------------------------------");
        System.out.println(TextColor.BLUE_TEXT + "[STUDENT]" + TextColor.RESET + " - [" + Thread.currentThread().getName()
                + "]" + " - Requesting to print " + document.getDocumentName()+ " with " + document.getNumberOfPages() + " pages");

        int pages = document.getNumberOfPages();
        while(pages > this.paperLevel || pages > this.tonerLevel){
            if(pages > this.paperLevel && pages > this.tonerLevel){
                System.out.println(TextColor.RED_TEXT + "[ERROR] [PAPER LEVEL = " + this.paperLevel + "] [TONER LEVEL = "
                        + this.tonerLevel + "]" + TextColor.RESET
                        + " - Not enough paper or toner to print. Please return later");
            }
            else if(pages > this.paperLevel){
                System.out.println(TextColor.RED_TEXT + "[ERROR] [PAPER LEVEL = " + this.paperLevel + "]" + TextColor.RESET
                        + " - Not enough paper to print. Please return later");
            }
            else {
                System.out.println(TextColor.RED_TEXT + "[ERROR] [TONER LEVEL = " + this.tonerLevel + "]" + TextColor.RESET
                        + " - Not enough toner to print. Please return later");
            }
            try {
                wait();
            }
            catch (InterruptedException e){
                System.out.println(e.getMessage());
            }
        }

        System.out.println(TextColor.BLUE_TEXT + "[STUDENT]" + TextColor.RESET + " - [" + Thread.currentThread().getName()
                + "]" + " - Printing document of " + document.getNumberOfPages() + "pages");

        paperLevel -= pages;
        tonerLevel -= pages;

        System.out.println(TextColor.BLUE_TEXT + "[STUDENT]" + TextColor.RESET + " - [" + Thread.currentThread().getName()
                + "]" + TextColor.GREEN_TEXT + " - Finished Printing a doc of pages  " + document.getNumberOfPages() + "pages"
                + TextColor.RESET);
        System.out.println("------------------------------------------------------------------------------------------------");
        notifyAll();
    }

    public boolean hasActiveStudents(){
        return students.activeCount() > 1;
    }

    @Override
    public synchronized void replaceTonerCartridge() {
        System.out.println("------------------------------------------------------------------------------------------------");
        System.out.println(TextColor.YELLOW_TEXT + "[TONER TECHNICIAN]" + TextColor.RESET + " - Trying to refill toner");

        while (tonerLevel >= MINIMUM_TONER_LEVEL){
            System.out.println(TextColor.RED_TEXT + "[WARNING] - Toner is available. Refill later" + TextColor.RESET);

            try {
                if (hasActiveStudents()){
                    wait(5000);
                }
                else {
                    return;
                }
            }
            catch (InterruptedException e){
                System.out.println(e.getMessage());
            }
        }

        System.out.println(TextColor.YELLOW_TEXT + "[TONER TECHNICIAN]" + TextColor.RESET +" - Refilling toner. Please Hold");
        tonerLevel = PAGES_PER_TONER_CARTRIDGE;
        cartridgesReplaced += 1;
        System.out.println(TextColor.YELLOW_TEXT + "[TONER TECHNICIAN]" +  TextColor.GREEN_TEXT + " - Toner refilled" + TextColor.RESET);
        System.out.println("------------------------------------------------------------------------------------------------");
        notifyAll();

    }

    @Override
    public synchronized void refillPaper() {
        System.out.println("------------------------------------------------------------------------------------------------");
        System.out.println(TextColor.PURPLE_TEXT + "[PAPER TECHNICIAN]" + TextColor.RESET + " - Trying to refill the paper");

        while (paperLevel + SHEETS_PER_PACK > FULL_PAPER_TRAY){
            System.out.println(TextColor.RED_TEXT + "[WARNING] - Cannot be refilled at the moment. Please check in later" + TextColor.RESET);

            try {
                if (hasActiveStudents()){
                    wait(5000);
                }
                else {
                    return;
                }
            }
            catch (InterruptedException e){
                System.out.println(e.getMessage());
            }
        }

        System.out.println(TextColor.PURPLE_TEXT + "[PAPER TECHNICIAN]" + TextColor.RESET + " - Refilling the printer. Please Hold");
        paperLevel += SHEETS_PER_PACK;
        packsFilled += 1;
        System.out.println(TextColor.PURPLE_TEXT + "[PAPER TECHNICIAN]" +  TextColor.GREEN_TEXT + " - Sheets refilled" + TextColor.RESET);
        System.out.println("------------------------------------------------------------------------------------------------");
        notifyAll();

    }

    public synchronized int getPacksFilled() {
        return packsFilled;
    }

    public synchronized int getCartridgesReplaced() {
        return cartridgesReplaced;
    }

    @Override
    public String toString() {
        return "LaserPrinter[" +
                "Printer Name='" + printerName + '\'' +
                ", Paper Level=" + paperLevel +
                ", Toner Level=" + tonerLevel +
                ']';
    }
}
