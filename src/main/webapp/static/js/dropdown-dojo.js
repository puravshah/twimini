dojo.addOnLoad(function() {
    var name = dojo.byId("username_hidden").value;
    var menu = new dijit.Menu({
        style: "display: none;"
    });
    var menuItem1 = new dijit.MenuItem({
        label: "Edit Profile",
        //iconClass: "dijitEditorIcon dijitEditorIconSave",
        onClick: function() {
            window.location = "/edit";
        }
    });
    menu.addChild(menuItem1);

    var menuItem2 = new dijit.MenuItem({
        label: "Logout",
        //iconClass: "dijitEditorIcon dijitEditorIconCut",
        onClick: function() {
            window.location = "/logout";
        }
    });
    menu.addChild(menuItem2);

    var button = new dijit.form.DropDownButton({
        label: name,
        name: "programmatic2",
        dropDown: menu,
        id: "progButton"
    });
    dojo.byId("dropdownButtonContainer").appendChild(button.domNode);
});