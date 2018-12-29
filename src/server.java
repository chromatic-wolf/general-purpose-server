import java.util.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class server{

    private static void printmsg(String message)
    {
        System.out.println(message);
        message = null;
    }
    
    private static void checkinternet()
    {
        try{
            String host="redhat.com";
            int port=80;
            int timeOutInMilliSec=5000;// 5 Seconds
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(host, port), timeOutInMilliSec);
printmsg("Internet is Available");
socket.close();

        }
        catch(Exception ex){
            printmsg("No Connectivity");
            
        }
    }
    
    
static boolean isPrime(long n) {
    //check if n is a multiple of 2
    if (n%2==0) return false;
    //if not, then just check the odds
    for(long i=3;i*i<=n;i+=2) {
        if(n%i==0)
            return false;
    }
    return true;
}


public static String getIp() throws Exception {
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            String ip = in.readLine();
            return ip;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


private static void filedowndel()
{
    String FILE_URL = "https://images.unsplash.com/photo-1477959858617-67f85cf4f1df?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80";
    String target = "test.jpg";
    


try (BufferedInputStream inputStream = new BufferedInputStream(new URL(FILE_URL).openStream());  
FileOutputStream fileOS = new FileOutputStream(target)) {
byte data[] = new byte[1024];
int byteContent;
while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
    fileOS.write(data, 0, byteContent);
}
} catch (IOException e) {
// handles IO exceptions
    printmsg("unable to download file");

}

File tmpDir = new File("test.jpg");
boolean exists = tmpDir.exists();
if (exists == true)
{
    printmsg("file exists");
    printmsg("deleting file");
    if(tmpDir.delete()) 
    { 
        printmsg("File deleted successfully"); 
    } 
    else
    { 
        printmsg("Failed to delete the file"); 
    } 

}else
{
    printmsg("file does not exist");

}
}

private static void calcprime()
{
    try(FileWriter fw = new FileWriter("primes.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
    {


    long maxval = 9223372036854775807L;
     for (long i = 0; i < maxval; i++)
     {
    boolean answer = isPrime(i);

        out.println(Long.toString(i) + " " + String.valueOf(answer));
     }
    }catch (IOException e) {
        printmsg("file writing failed");

        //exception handling left as an exercise for the reader
    }
    printmsg("Completed calculating prime nimbers from");
    printmsg("0 to 9223372036854775807");
}

public static String formatFileSize(long size) {
    String hrSize = null;

    double b = size;
    double k = size/1024.0;
    double m = ((size/1024.0)/1024.0);
    double g = (((size/1024.0)/1024.0)/1024.0);
    double t = ((((size/1024.0)/1024.0)/1024.0)/1024.0);

    DecimalFormat dec = new DecimalFormat("0.00");

    if ( t>1 ) {
        hrSize = dec.format(t).concat(" TB");
    } else if ( g>1 ) {
        hrSize = dec.format(g).concat(" GB");
    } else if ( m>1 ) {
        hrSize = dec.format(m).concat(" MB");
    } else if ( k>1 ) {
        hrSize = dec.format(k).concat(" KB");
    } else {
        hrSize = dec.format(b).concat(" Bytes");
    }

    return hrSize;
}

public static Long convertToLong(Object o){
    String stringToConvert = String.valueOf(o);
    Long convertedLong = Long.parseLong(stringToConvert);
    return convertedLong;

}


private static void getcpu()
{
    printmsg("----------hardware info-------------");
    OperatingSystemMXBean osMBean =
            (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
     printmsg("Operating system:\t" + osMBean.getName());
     printmsg("Architecture:\t\t" + osMBean.getArch());
     printmsg("Number of processors:\t" + osMBean.getAvailableProcessors());
     OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
          method.setAccessible(true);
          if (method.getName().startsWith("getTotal") || method.getName().startsWith("getFree")
              && Modifier.isPublic(method.getModifiers())) {
                  Object value;
              try {
                  value = method.invoke(operatingSystemMXBean);
              } catch (Exception e) {
                  value = e;
              } // try
              printmsg(method.getName() + " = " + formatFileSize(convertToLong(value)));
          } // if
        } // for    
     printmsg("--------------------------------");

     
        printmsg("----------java info-------------");
        printmsg("Java version: " + System.getProperty("java.version"));
    printmsg("Free memory: " + 
            formatFileSize(Runtime.getRuntime().freeMemory()));
    long maxMemory = Runtime.getRuntime().maxMemory();
    /* Maximum amount of memory the JVM will attempt to use */
    printmsg("Maximum java memory: " + 
        (maxMemory == Long.MAX_VALUE ? "no limit" : formatFileSize(maxMemory)));
    printmsg("Total memory available to JVM: " + 
            formatFileSize(Runtime.getRuntime().totalMemory()));
    printmsg("------------------------------------");

    printmsg("----------file system-------------");
    File[] roots = File.listRoots();
    for (File root : roots) {
        printmsg("File system root: " + root.getAbsolutePath());
        printmsg("Total space: " + formatFileSize(root.getTotalSpace()));
        printmsg("Free space: " + formatFileSize(root.getFreeSpace()));
        printmsg("Usable space: " + formatFileSize(root.getUsableSpace()));
      }
    printmsg("----------------------------------");
    
    printmsg("----------system info-------------");
try
{
printmsg("username: " + System.getProperty("user.name"));
}catch(NullPointerException e)
{
    printmsg("username: NULL");
}
DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
LocalDateTime now = LocalDateTime.now();  
printmsg("Date: " + dtf.format(now)); 

dtf = null;
now = null;

long upTime;
try {
    upTime = getSystemUptime();
    printmsg("uptime: " + upTime + " ms");
    
    
    Date date = new Date(upTime);
  
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
    formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
  
    String formatted = formatter.format(date);
    printmsg("Uptime: " + formatted);
    
    date = null;
    formatter = null;
    formatted = null; 
    upTime = 0;
    
} catch (Exception e) {
    e.printStackTrace();
}

try {
    InetAddress i = InetAddress.getLocalHost();
    printmsg("computer name: " + i.getHostName());    // name
    printmsg("internal ip: " + i.getHostAddress()); // IP address only
i = null;


} catch (Exception e) {
    e.printStackTrace();
}
    try
    {
        
    
Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
printmsg("screen height: " + screenSize.getHeight());
printmsg("screen width: " + screenSize.getWidth());
screenSize = null;
    }catch(java.awt.HeadlessException e)
    {
        printmsg("there is no display (headless)");
    }
    printmsg("----------------------------------");

}


public static long getSystemUptime() throws Exception {
    long uptime = -1;
    String os = System.getProperty("os.name").toLowerCase();
    if (os.contains("win")) {
        Process uptimeProc = Runtime.getRuntime().exec("net stats srv");
        BufferedReader in = new BufferedReader(new InputStreamReader(uptimeProc.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            if (line.startsWith("Statistics since")) {
                SimpleDateFormat format = new SimpleDateFormat("'Statistics since' MM/dd/yyyy hh:mm:ss a");
                Date boottime = format.parse(line);
                uptime = System.currentTimeMillis() - boottime.getTime();
                break;
            }
        }
    } else if (os.contains("mac") || os.contains("nix") || os.contains("nux") || os.contains("aix")) {
        Process uptimeProc = Runtime.getRuntime().exec("uptime");
        BufferedReader in = new BufferedReader(new InputStreamReader(uptimeProc.getInputStream()));
        String line = in.readLine();
        if (line != null) {
            Pattern parse = Pattern.compile("((\\d+) days,)? (\\d+):(\\d+)");
            Matcher matcher = parse.matcher(line);
            if (matcher.find()) {
                String _days = matcher.group(2);
                String _hours = matcher.group(3);
                String _minutes = matcher.group(4);
                int days = _days != null ? Integer.parseInt(_days) : 0;
                int hours = _hours != null ? Integer.parseInt(_hours) : 0;
                int minutes = _minutes != null ? Integer.parseInt(_minutes) : 0;
                uptime = (minutes * 60000) + (hours * 60000 * 60) + (days * 6000 * 60 * 24);
            }
        }
    }
    return uptime;
}



private static void getProcess()
{
    OperatingSystemMXBean osMBean =
            (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
     String os = osMBean.getName();
     
     if(os.contentEquals("Linux"))
        {
         try {
                String line;
                Process p = Runtime.getRuntime().exec("ps -e");
                BufferedReader input =
                        new BufferedReader(new InputStreamReader(p.getInputStream()));
                while ((line = input.readLine()) != null) {
printmsg(line); //<-- Parse data here.
                }
                input.close();
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
     if(os.contentEquals("Windows 10") || os.contentEquals("Windows 8") || os.contentEquals("Windows 7") || os.contentEquals("Windows xp") || os.contentEquals("Windows vista"))
        {
         try {
                String line;
                Process p = Runtime.getRuntime().exec
                        (System.getenv("windir") +"\\system32\\"+"tasklist.exe");
                BufferedReader input =
                        new BufferedReader(new InputStreamReader(p.getInputStream()));
                while ((line = input.readLine()) != null) {
                    printmsg(line); //<-- Parse data here.
                }
                input.close();
            } catch (Exception err) {
                err.printStackTrace();
            }   
        }
     if (!os.contentEquals("Linux") && !os.contentEquals("Windows 10") && !os.contentEquals("Windows 8") && !os.contentEquals("Windows 7") && !os.contentEquals("Windows xp") && !os.contentEquals("Windows vista"))
        {
         printmsg(os);
         printmsg("you are either using mac (OSX) or an unknown OS");
        }
    
    
}

private static void downloadfile()
{
    List<String> urls = new ArrayList<>();
    List<String> names = new ArrayList<>();
    
    
    Scanner urlreader = new Scanner(System.in);
    Scanner namereader = new Scanner(System.in);
    printmsg("please enter the a URL and press enter");
    printmsg("press enter without any text to continue");
    String message = "";
    while(true)
    {
        printmsg(message);
        String url = urlreader.nextLine();
        
        if (url == null)
        {
            printmsg("null");
            break;
        }
        if (url.length() == 0)
        {
            printmsg("nothing");
            break;
        }
                
                while(true)
                {
                    printmsg("enter a name");
                    String name = namereader.nextLine();
                    if(name == null)
                    {
                    printmsg("you did not enter a name");   
                    }
                    if (name.length() == 0)
                    {
                        printmsg("you did not enter a name");
                    }
                    if (name.length() != 0 && name != null)
                    {
                        names.add(name);
                        printmsg("name: " + name);
                        break;
                    }
                }
                urls.add(url);

                printmsg("using url: " + url);
                printmsg("added");
                
        message = "enter another url or press enter";
    }
    urlreader.close();
    namereader.close();
    
    Scanner input = new Scanner(System.in);

    
    printmsg("would you like to");
    printmsg("1: Download 1 at a time");
    printmsg("2: Download all at once");
    printmsg("0: To cancel");

    int option = input.nextInt();
    
    if (option == 1)
    {
        
        for(int i = 0; i < names.size(); i++) {
            printmsg(names.get(i));
        }
        
        
    }
    if (option == 2)
    {
        
    }
    if (option == 0)
    {
        
    }
if (option != 1 && option != 2 && option != 0)
{
    printmsg("you did not enter a valid number " + option);
}
    input.close();
    
}



public static void startserver()
{
    printmsg("feature not implemented yet");

} 

public static Future<ScanResult> portIsOpen(final ExecutorService es, final String ip, final int port,
        final int timeout) {
    return es.submit(new Callable<ScanResult>() {
        @Override
        public ScanResult call() {
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port), timeout);
                socket.close();
                return new ScanResult(port, true);
            } catch (Exception ex) {
                return new ScanResult(port, false);
            }
        }
    });
}

public static class ScanResult {
    private int port;

    private boolean isOpen;

    public ScanResult(int port, boolean isOpen) {
        super();
        this.port = port;
        this.isOpen = isOpen;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

}



     public static void main(String []args)
     {
         int option;
         Scanner input = new Scanner(System.in);
         do{
           printmsg("what do you want to do");
         printmsg("1: check internet");
        printmsg("2: get external ip");
        printmsg("3: check internet and file read/write");
        printmsg("4: calculate a lot of prime numbers");
        printmsg("5: get system info");
        printmsg("6: get running procceses");
        printmsg("7: start a networking server");
        printmsg("8: Download a file");
        printmsg("9: Port scan a target");
        printmsg("0: Exit");
try
{
    option = input.nextInt();   

             
                if (option ==  0) { 
                    printmsg("exiting application");
                    input.close();
               System.exit(0);
                }
                if (option == 1)
                {
                    checkinternet();
                }
                if (option == 2)
                {
                    try {
                        printmsg(getIp());
                    } catch (Exception e) {
                        printmsg("could not get external ip");
                        e.printStackTrace();
                    }
                }
                if (option == 3)
                {
                    filedowndel();
                }
                if (option == 4)
                {
                    calcprime();
                }
                if (option == 5)
                {
                    getcpu();
                }
                if (option == 6)
                {
                    getProcess();
                }
                if (option == 7)
                {
                    startserver();
                }
                if (option == 8)
                {
                    downloadfile();
                }
                if (option == 9)
                {
                     Scanner targetscanner = new Scanner(System.in);

                    final ExecutorService es = Executors.newFixedThreadPool(20);
                    printmsg("Enter a target ip");
                    
                    final String ip = targetscanner.nextLine();
                    final int timeout = 200;
                    final List<Future<ScanResult>> futures = new ArrayList<>();
                    for (int port = 1; port <= 65535; port++) {
                        // for (int port = 1; port <= 80; port++) {
                        futures.add(portIsOpen(es, ip, port, timeout));
                    }
                    es.awaitTermination(200L, TimeUnit.MILLISECONDS);
                    int openPorts = 0;
                    for (final Future<ScanResult> f : futures) {
                        if (f.get().isOpen()) {
                            openPorts++;
                            System.out.println(f.get().getPort());
                        }
                    }
                    targetscanner.close();
                }
                
                if(option != 0 && option != 1 && option != 2 && option != 3 && option != 4 && option != 5 && option != 6 && option != 7 && option != 8 && option != 8)    
                {
                    printmsg(option + " is an invalid option");

                    
                }
}catch(InputMismatchException e)
{
    
    printmsg("you did not enter a number");
    input.close();

    break;
    
} catch (InterruptedException e) {
    e.printStackTrace();
} catch (ExecutionException e) {
    e.printStackTrace();
}

            } while(true); 

         
 

        
        


     } 
}