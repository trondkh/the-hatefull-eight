package server;
/*
https://stackoverflow.com/questions/921262/how-to-download-and-save-a-file-from-internet-using-java
denne skal kunne gjøre jobben. Vi henter filen fra server og parser den lokalt på pien
*/

public class Server {
	
	// prøver å heten json-fil fra server
	
	public Server(){
        fetchFile();
    }
    
    public void fetchFile(){
	try{
		// noe er galt med URL
		org.apache.commons.io.FileUtils.copyURLToFile("org.ntnu.no/trd", "test.json");
	
	}catch(Exception e){
	        System.out.println(e);
	    }
	}
    // lager en main for å teste den
    public static void main(String[] args){
        new Server();
    }
	
}	
