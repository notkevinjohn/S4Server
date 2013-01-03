package NoLongerUsed;

import java.io.Serializable;
import java.util.Vector;

import Data.PayloadDeviceNameList;

public class PayloadListVector implements Serializable
{
	private static final long serialVersionUID = 201039042641623708L;
	
	public Vector<PayloadDeviceNameList> terminalPayloadList;
}
