# IP_Header_Checksum_Verification

Checksum at the Sender and at the Receiver
	
	The checksum in the IPv4 header field is used to determine if a header has been corrupted. The checksum is verified at each hop. If no corruption exists, the result of adding up the entire header should be ffff. This verification is done at receiving nodes, and any packets with non-matching checksums are dropped. 
	The sending router makes adjustments to the checksum if it needs to change IP header fields. The sender calculates the checksum by computing the sum of every 16-bit value in the header, excluding the checksum (which is set to 0). The same procedure is used to verify the checksum however, the original checksum is included. 


Listing of Programs

	SorensenMichael_Project3.java
		-This program includes the following methods:
			-hexStringToByteArray:
				-Converts the user input string of hex symbols into a byte array
				which holds the hex values in pairs
			-calculateChecksum:
				-Calculates the checksum of the packet before it is sent.
			-verifyChecksum:
				-Calculates the addition of all 16-bit hex words in the 
				packet received (which includes the checksum calculated by
				the sender).
				-If the checksum is correct still in the received packet, this method
				returns 'ffff' (a result of all 1's). 

