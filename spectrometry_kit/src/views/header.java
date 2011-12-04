class VideoRowButton extends Button {
  VideoRowButton(String PbuttonName,int Px,int Py,int Pheight) { super(PbuttonName,Px,Py,Pheight); }
  String forController = "setup"; // or "all"
  void draw() {
    if (controller == "setup") { 
	super.draw(); 
    }
  }
  void mousePressed() {
    if (controller == "setup" && super.mouseOver()) {
      setup.selectingSampleRow = true;
    }
  }
}

class SaveButton extends Button {
  SaveButton(String PbuttonName,int Px,int Py,int Pheight) { super(PbuttonName,Px,Py,Pheight); }
  String forController = "all";
  void mousePressed() {
    if (super.mouseOver()) server.upload();
  }
}

class AnalyzeButton extends Button {
  AnalyzeButton(String PbuttonName,int Px,int Py,int Pheight) { super(PbuttonName,Px,Py,Pheight); }
  String forController = "all";
  void mousePressed() {
    if (super.mouseOver()) {
      header.switchController("analyze");
      header.heatmapButton.up();
      header.setupButton.up();
      header.analyzeButton.down();
    }
  }
}

class HeatmapButton extends Button {
  HeatmapButton(String PbuttonName,int Px,int Py,int Pheight) { super(PbuttonName,Px,Py,Pheight); }
  String forController = "all";
  void mousePressed() {
    if (super.mouseOver()) {
      header.switchController("heatmap");
      header.heatmapButton.down();
      header.setupButton.up();
      header.analyzeButton.up();
    }
  }
}

class SetupButton extends Button {
  SetupButton(String PbuttonName,int Px,int Py,int Pheight) { super(PbuttonName,Px,Py,Pheight); }
  String forController = "all";
  void mousePressed() {
    if (super.mouseOver()) {
      header.switchController("setup");
      header.heatmapButton.up();
      header.setupButton.down();
      header.analyzeButton.up();
    }
  }
}

class BaselineButton extends Button {
  BaselineButton(String PbuttonName,int Px,int Py,int Pheight) { super(PbuttonName,Px,Py,Pheight); }
  String forController = "analyze";
  void mousePressed() {
    if (super.mouseOver()) spectrum.storeReference();
  }
  void draw() {
    if (controller == "analyze") super.draw();
  }
}

class WebcamButton extends Button {
  WebcamButton(String PbuttonName,int Px,int Py,int Pheight) { super(PbuttonName,Px,Py,Pheight); }
  String forController = "setup";
  void draw() {
    if (video.isLinux) { super.draw(); }
  }
  void mousePressed() {
    if (super.mouseOver() && video.isLinux) {
      video.changeDevice(video.device+1);
    }
  }
}

class LearnButton extends Button {
  LearnButton(String PbuttonName,int Px,int Py,int Pheight) { super(PbuttonName,Px,Py,Pheight); }
  String forController = "all";
  void mousePressed() {
    if (super.mouseOver()) link("http://publiclaboratory.org/wiki/spectral-workbench");
  }
}

// Begin header view:
// Right now, this defines both the presentation (the buttons)
// and the functions they represent.
class Header {
  public PImage logo;
  public int rightOffset = 0; // where to put new buttons (shifts as buttons are added)
  public ArrayList buttons; 
  public Button learnButton;
  public Button saveButton;
  public Button analyzeButton;
  public Button heatmapButton;
  public Button setupButton;

  public Button baselineButton;
  public Button webcamButton;
  public Button videoRowButton;
  public int margin = 4;

  public Header() {
    logo = loadImage("logo-small.png");
    buttons = new ArrayList();
    learnButton = addButton(new LearnButton("Learn",width-rightOffset-margin,margin,headerHeight-8));
    saveButton = addButton(new SaveButton("Save",width-rightOffset-margin,margin,headerHeight-8));

    analyzeButton = addButton(new AnalyzeButton("Analyze",width-rightOffset-margin,margin,headerHeight-8));
    analyzeButton.down();
    heatmapButton = addButton(new HeatmapButton("Heatmap",width-rightOffset-margin,margin,headerHeight-8));
    setupButton = addButton(new SetupButton("Setup",width-rightOffset-margin,margin,headerHeight-8));

    baselineButton = addButton(new BaselineButton("Baseline",width-rightOffset-margin,margin,headerHeight-8));
    baselineButton.fillColor = #444444;
    webcamButton = addButton(new WebcamButton("Switch webcam",width-rightOffset-margin,margin,headerHeight-8));
    webcamButton.fillColor = #444444;
    videoRowButton = addButton(new VideoRowButton("Adjust sample row",width-rightOffset-margin,margin,headerHeight-8));
    videoRowButton.fillColor = #444444;
  }

  public Button addButton(Button pButton) {
    buttons.add(pButton);
    rightOffset += pButton.width+margin;
    pButton.x -= pButton.width; 
    return pButton;
  }
  public Button addButton(String buttonName) {
    Button button = new Button(buttonName,width-rightOffset-margin,margin,headerHeight-8);
    addButton(button);
    return button;
  }

  public void mousePressed() {
    for (int i = 0;i < buttons.size();i++) {
      Button b = (Button) buttons.get(i);
      b.mousePressed();
    }
  }

  public void switchController(String Pcontroller) {
    controller = Pcontroller;
    
    // for (int i = 0;i < buttons.size();i++) {
    //   if (buttons.get(i).forController != "all" && buttons.get(i).forController != Pcontroller) {
    //     rightOffset -= buttons.get(i).width;
    //   } else {
    //     buttons.get(i).x = width-rightOffset-margin;
    //     rightOffset += buttons.get(i).width;
    //   }
    // }
  }

  public void draw() {
    fill(255);
    noStroke();
    image(logo,14,14);
    textFont(font,24);
    text("PLOTS Spectral Workbench: "+typedText, 55, 40); //display current title
    
    for (int i = 0;i < buttons.size();i++) {
      Button b = (Button) buttons.get(i);
      b.draw();
    }
  }
}


