package twins.logic;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import twins.additionalClasses.ItemId;
import twins.boundaries.ItemBoundary;
import twins.boundaries.OperationBoundary;
import twins.dal.ItemHandler;
import twins.dal.OperationHandler;
import twins.dal.UserHandler;
import twins.data.ItemEntity;
import twins.data.UserEntity;

@Component
public class OperationComponent {
	private OperationHandler operationHandler;
	private ItemHandler itemHandler;
	private UserHandler userHandler;
	private ObjectMapper jackson;

	
	@Autowired
	public OperationComponent(OperationHandler operationHandler) {
		super();
		this.operationHandler = operationHandler;
		this.jackson = new ObjectMapper();
	}
	
	@Autowired
	public void setItemHandler(ItemHandler itemHandler) {
		this.itemHandler = itemHandler;
	}
	
	@Autowired
	public void setUserHandler(UserHandler userHandler) {
		this.userHandler = userHandler;
	}
//add json for attributes -> use get to get values
	/*
	 * TODO
	 * i have stated the class according to what Eyal said in the class, i'm sending operation boundary 
	 * from the operations service implementation.
	 * i based this idea on https://drive.google.com/file/d/1iirTcReIADWD7sS0AhNBPrkMZxgYJakA/view
	 * what i tried to start:
	 * first you get from the operation boundary the item type so you can know what method to invoke (inside this class)
	 * then you need to check the type to know what case to enter -> this is where i stopped
	 * ---------------------
	 * what i think you should try is to get inside each method in the item entity the map of the attributes to check -> for example the max capacity for this attraction
	 * and i also thought that the list of user being added to this activity is based in operations attributes map -> for easier work we decide that there can be only 2 types of users inside the map: 
	 * 1) Instructor - User with UserRole = "MANAGER" (only 1 for each activity)
	 * 2) member - User with UserRole = "PLAYER" (2-3 just so we'll have what to work with)
	 * also the last thing Eyal added (Message initializer might make it easier - look for the profile in the properties)
	 */
	public Object switchCase(OperationBoundary operationBoundary) {
		String type = operationBoundary.getType();
		System.err.println("in operation component - switch case " + type);
		Optional<ItemEntity> itemOptional = this.itemHandler.findById(this.marshall(new ItemId(operationBoundary.getItem().getItemId().getSpace(),operationBoundary.getItem().getItemId().getID())));
		Optional<UserEntity> userOptional = this.userHandler.findById(operationBoundary.getInvokedBy().getUserId().getSpace() + "|" + operationBoundary.getInvokedBy().getUserId().getEmail());
		if(itemOptional.isPresent() && userOptional.isPresent()) {
			ItemEntity ie = itemOptional.get();
			UserEntity ue = userOptional.get();
			switch (type) {

			case "Book field":
				/*if(ie.getType() == "Soccer") //operationBoundary.getOperationAttributes().containsKey("Soccer")
					reserveSoccerField(operationBoundary, ie);
				else if(ie.getType() == "Tennis")
					//reserveTennisCourt();
				else if(ie.getType() == "Basketball")
					//reserveBasketballCourt();*/
				break;

			case "Extra activities":
			/*	if(ie.getType() == "Yoga")
					//signToYoga();
				else if(ie.getType() == "HipHop")
					//signtoHipHop();*/
			default:
				break;
			}
		}else {
			throw new RuntimeException("");
		}
		System.err.println("before return");
		return operationBoundary;
	}
	/*private void reserveSoccerField(OperationBoundary operationBoundary, ItemEntity ie) {
		
		if(ie.getItemAttributes().contains("capacity"){
			
		}
	}*/
	
	private String marshall(Object value) {
		try {
			return this.jackson
					.writeValueAsString(value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
}
