
import java.util.*;
import java.lang.*;

public class SorensenMichael_Project3
{
	public static void main (String[] args)
	{
		//Create Scanner object to get packet hexdump input
		Scanner Keyboard = new Scanner(System.in);

		//Prompt user to enter hexdump
		System.out.print("Enter Packet Header's Hexdump ");

		//Get the input as a String
		String hexInput = Keyboard.nextLine();

		//Get any blank spaces out of hexDump
		String hexNoSpace = hexInput.replaceAll("\\s", "");

		//call method to convert hexNoSpace into a byte array
		byte [] hexArrayByte = hexStringToByteArray(hexNoSpace);

		//call method to get the checksum calculated by the sender
		// into a long variable
		long checksum = calculateChecksum(hexArrayByte);

		//convert the checksum back to a string to display in console
		//then print
		String checksumHexString = Long.toHexString(checksum);

		System.out.println("The checksum calculated by the sender is: " + checksumHexString);

		//Insert the checksum into the header by replacing the 0's
		//at the string location of the checksum.
		//Create a new hex dump string for the complete header
	  String hexResultNoSpace	= hexNoSpace.substring(0,20) + checksumHexString
		+ hexNoSpace.substring(24);

		//Insert spaces every 2 characters for a neater output
		//Print resulting packet recieved
		String space = "2";
		String hexResultPkt = hexResultNoSpace.replaceAll("(.{" + space + "})",
		"$1 ").trim();

		System.out.println("The resluting packet (recieved) is: " + hexResultPkt);

  //Reciever can now calculate the checksum and add it to the result
	//to verify if the checksum is correct. The result should be ffff.

	  //Create a new byte array for the header that includes the checksum.
		byte [] verifyArray = hexStringToByteArray(hexResultNoSpace);
		//Send the resulting packet through the verify method, store
		//in a long type variable
		long checksumComplement = verifyChecksum(verifyArray);
		//Convert to string and print calculation to console.
		String complementString = Long.toHexString(checksumComplement);
		System.out.println("Checksum verification at reciever: " + complementString);


	}




	//method to convert the hex string input to a byte
	//array we can feed into the calculateChecksum method
			public static byte[] hexStringToByteArray(String s) {
    int len = s.length();
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                             + Character.digit(s.charAt(i+1), 16));
      }
      return data;
   }


	 //Method to calculate the checksum and return it.
	 //Takes byte array created by hexStringToByteArray method
	 //as input.
	 public static long calculateChecksum(byte[] buf) {
    int length = buf.length;
    int i = 0;

    long sum = 0;
    long data;

    //Handle all pairs
    while (length > 1) {
      //Get values from the hex byte array, stepping through it
			//to convert the byte values to hex then adding them to the
			//rolling sum value
      data = (((buf[i] << 8) & 0xFF00) | ((buf[i + 1]) & 0xFF));
      sum += data;
      //if there is a carry bit, add 1 to the sum (end-around carry)
      if ((sum & 0xFFFF0000) > 0) {
        sum = sum & 0xFFFF;
        sum += 1;
      }

      i += 2;
      length -= 2;
    }

    //Handle the last byte in odd length header hexdumps
    if (length > 0) {
      sum += (buf[i] << 8 & 0xFF00);
      //end-around carry added to sum
      if ((sum & 0xFFFF0000) > 0) {
        sum = sum & 0xFFFF;
        sum += 1;
      }
    }

    //Last 1's complement value correction to 16-bits
    sum = ~sum; //we take this statement out in the verification method below
    sum = sum & 0xFFFF;
    return sum;

  }




	//Method to verify the checksum using the newly determined
	//resulting packet. Returns the result of adding all header bytes
	//plus the checksum. If it returns ffff, the checksum is verified.
	public static long verifyChecksum(byte[] buf) {
	 int length = buf.length;
	 int i = 0;

	 long sum = 0;
	 long data;

	 //Handle all pairs
	 while (length > 1) {
		 //Get values from the hex byte array, stepping through it
		 //to convert the byte values to hex then adding them to the
		 //rolling sum value
		 data = (((buf[i] << 8) & 0xFF00) | ((buf[i + 1]) & 0xFF));
		 sum += data;
		 //if there is a carry bit, add 1 to the sum (end-around carry)
		 if ((sum & 0xFFFF0000) > 0) {
			 sum = sum & 0xFFFF;
			 sum += 1;
		 }

		 i += 2;
		 length -= 2;
	 }

	 //Handle the last byte in odd length header hexdumps
	 if (length > 0) {
		 sum += (buf[i] << 8 & 0xFF00);
	 //end-around carry added to sum
		 if ((sum & 0xFFFF0000) > 0) {
			 sum = sum & 0xFFFF;
			 sum += 1;
		 }
	 }

	 //Final result of the sum calculated over the
	 //entire packet including the checksum. value corrected to 16-bits

	 sum = sum & 0xFFFF;
	 return sum;

 }



}
