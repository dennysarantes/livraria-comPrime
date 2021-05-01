package br.com.caelum.livraria.view;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.component.chip.Chip;

@RequestScoped
public class ChipView {

    public void onSelect(AjaxBehaviorEvent e) {
        String label = ((Chip) e.getSource()).getLabel();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Select Event", label + " selected.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void onClose() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Close Event", "Chip closed.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}