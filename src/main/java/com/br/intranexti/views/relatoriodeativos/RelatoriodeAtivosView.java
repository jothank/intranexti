package com.br.intranexti.views.relatoriodeativos;

import com.br.intranexti.data.SamplePerson;
import com.br.intranexti.services.SamplePersonService;
import com.br.intranexti.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import jakarta.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@PageTitle("Relatorio de Ativos")
@Route(value = "relatorioativo", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@Uses(Icon.class)
public class RelatoriodeAtivosView extends Composite<VerticalLayout> {

    public RelatoriodeAtivosView() {
        ComboBox comboBox = new ComboBox();
        MessageInput messageInput = new MessageInput();
        MenuBar menu = new MenuBar();
        RouterLink routerLink = new RouterLink();
        LoginForm loginForm = new LoginForm();
        Grid basicGrid = new Grid(SamplePerson.class);
        getContent().setHeightFull();
        getContent().setWidthFull();
        comboBox.setLabel("Combo Box");
        setComboBoxSampleData(comboBox);
        setMenuSampleData(menu);
        routerLink.setText("Custom View");
        routerLink.setRoute(CustomView.class);
        setGridSampleData(basicGrid);
        getContent().add(comboBox);
        getContent().add(messageInput);
        getContent().add(menu);
        getContent().add(routerLink);
        getContent().add(loginForm);
        getContent().add(basicGrid);
    }

    record SampleItem(String value, String label, Boolean disabled) {
    }

    private void setComboBoxSampleData(ComboBox comboBox) {
        List<SampleItem> sampleItems = new ArrayList<>();
        sampleItems.add(new SampleItem("first", "First", null));
        sampleItems.add(new SampleItem("second", "Second", null));
        sampleItems.add(new SampleItem("third", "Third", Boolean.TRUE));
        sampleItems.add(new SampleItem("fourth", "Fourth", null));
        comboBox.setItems(sampleItems);
        comboBox.setItemLabelGenerator(item -> ((SampleItem) item).label());
    }

    private void setMenuSampleData(MenuBar menuBar) {
        menuBar.addThemeVariants(MenuBarVariant.LUMO_ICON);
        MenuItem share = menuBar.addItem(new Text("Share"));
        share.add(new Icon(VaadinIcon.ANGLE_DOWN));
        SubMenu shareSubMenu = share.getSubMenu();
        MenuItem onSocialMedia = shareSubMenu.addItem("On social media");
        SubMenu socialMediaSubMenu = onSocialMedia.getSubMenu();
        socialMediaSubMenu.addItem("Facebook");
        socialMediaSubMenu.addItem("Twitter");
        socialMediaSubMenu.addItem("Instagram");
        shareSubMenu.addItem("By email");
        shareSubMenu.addItem("Get Link");
    }

    private void setGridSampleData(Grid grid) {
        grid.setItems(query -> samplePersonService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
    }

    @Autowired()
    private SamplePersonService samplePersonService;
}
