package kegelmeisterschaft.model.result;

import kegelmeisterschaft.entities.EventBean;
import kegelmeisterschaft.entities.ResultBean;
import kegelmeisterschaft.model.EventModel;

public class PlayerDetailsResultModel {

    private EventModel event;
    private ResultBean result;

    public PlayerDetailsResultModel(EventBean event, ResultBean result) {
	this.event = new EventModel(event);
	this.result = result;
    }

    public EventModel getEvent() {
	return event;
    }

    public ResultBean getResult() {
	return result;
    }

}
