package novus.gdx.controller;

import com.badlogic.gdx.controllers.*;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.badlogic.gdx.*;

public class ControllerHandler implements ControllerListener {
	
	private Map<String,Controller> playerControllersMap = new ConcurrentHashMap<>();
//	private Map<Controller,Integer> btnCodeMap = new ConcurrentHashMap<>();
//	private Map<Controller,Integer> axisXCodeMap = new ConcurrentHashMap<>();
//	private Map<Controller,Integer> axisYCodeMap = new ConcurrentHashMap<>();
//	private Map<Controller,Integer> povXCodeMap = new ConcurrentHashMap<>();
//	private Map<Controller,Integer> povYCodeMap = new ConcurrentHashMap<>();
	//private Map<Controller,Integer> controllers = new HashMap<>();
	private Boolean hasControllers;
	
	public ControllerHandler( ) {
		int i = 1;
		for (Controller controller : Controllers.getControllers()) {
			controller.addListener(this);
			playerControllersMap.put("P" + i, controller);
			Gdx.app.log("Controller P"+i, playerControllersMap.get("P"+i).getName());
			i++;
		}
		if(Controllers.getControllers().size == 0)
        {
            hasControllers = false;
        }
	}
	
	public Map<String, Controller> getPlayersController() {
		return playerControllersMap;
	}
	public Controller getPlayerController(String key) {
		return playerControllersMap.get(key);
	}
	
	 // connected and disconnect dont actually appear to work for XBox 360 controllers.
    @Override
    public void connected(Controller controller) {
        hasControllers = true;
    }

    @Override
    public void disconnected(Controller controller) {
        hasControllers = false;
    }

	@Override
	public boolean buttonDown(Controller controller, int buttonCode) {
		//btnCodeMap.put(controller, buttonCode);
		
//		if(buttonCode == XBox360Pad.BUTTON_A) {
//			btnCodeMap.put(controller, buttonCode);
//		}
//		if(buttonCode == XBox360Pad.BUTTON_B) {
//			btnCodeMap.put(controller, buttonCode);		
//		}
//		if(buttonCode == XBox360Pad.BUTTON_X) {
//			
//		}
//		if(buttonCode == XBox360Pad.BUTTON_Y) {
//			
//		}
		
        return false;
	}

	@Override
	public boolean buttonUp(Controller controller, int buttonCode) {
		// TODO Auto-generated method stub
		//btnCodeMap.put(controller, null);
		return false;
	}

	@Override
	public boolean axisMoved(Controller controller, int axisCode, float value) {
		
//		if(value > 0.3 || value < -0.3 ) {
//			Gdx.app.log("Controller:"+ controller, ""+ axisCode +" " + value);
//		}
//		
		// TODO Auto-generated method stub
//		if(axisCode == XBox360Pad.AXIS_LEFT_X || axisCode == XBox360Pad.AXIS_RIGHT_X) {
//			axisXMap.put(controller, value);
//		}
//		if(axisCode == 2 || axisCode == 3) {
//			axisYMap.put(controller, value);
//		}
//
//		
		return false;
	}

	@Override
	public boolean povMoved(Controller controller, int povCode, PovDirection value) {
		
		// TODO Auto-generated method stub
		//Gdx.app.log("Controller:"+ controller.getName(), " -  "+povCode + "-  " + value);
		return false;
	}

	@Override
	public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
		// TODO Auto-generated method stub
		return false;
	}
}
