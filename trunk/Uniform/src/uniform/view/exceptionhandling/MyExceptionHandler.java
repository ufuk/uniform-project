package uniform.view.exceptionhandling;


import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

public class MyExceptionHandler extends ExceptionHandlerWrapper {
	 
	private ExceptionHandler wrapped;
	 
	public MyExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}
	
	@Override
	public ExceptionHandler getWrapped() {
		return wrapped;
	}
	
	@Override
	public void handle() throws FacesException {
		for (Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator(); i.hasNext(); ) {  
			ExceptionQueuedEvent exceptionQueuedEvent = i.next();
			ExceptionQueuedEventContext exceptionQueuedEventContext = 
						(ExceptionQueuedEventContext) exceptionQueuedEvent.getSource();
			exceptionQueuedEventContext.getException();
            try {
            	FacesContext.getCurrentInstance().getApplication().getNavigationHandler()
            				.handleNavigation(FacesContext.getCurrentInstance(), 
            								  null, 
            								  "errorPage?faces-redirect=true");
            } finally {
            	i.remove();
            }
		}
		getWrapped().handle();
	}

}