import React from 'react';

import SseToolbar from "../../common/SseToolbar";
import SseBranding from "../../common/SseBranding";
import {
    CircleOutline, FileDownloadOutline, Gesture, Minus, Plus, PlusMinus, Redo, SquareOutline,
    Undo,
    ArrowRight,
    ArrowLeft,
    Home,
    Refresh,
    ViewColumn,
    Help
} from 'mdi-material-ui';

export default class SseToolbar3d extends SseToolbar {

    constructor() {
        super();
        this.state = {pointSize: 2}
    }


    componentDidMount() {
        super.componentDidMount();

        this.addCommand("returnFolderCommand","Return Folder",0,"W","return",Home,undefined,undefined);

        this.addCommand("changeBoxCommand0","Box Size",0,"shift+q","minx+",Help,undefined,undefined);
        this.addCommand("changeBoxCommand1","Box Size",0,"shift+w","minx-",Help,undefined,undefined);
        this.addCommand("changeBoxCommand2","Box Size",0,"shift+e","miny+",Help,undefined,undefined);
        this.addCommand("changeBoxCommand3","Box Size",0,"shift+r","miny-",Help,undefined,undefined);
        this.addCommand("changeBoxCommand4","Box Size",0,"shift+a","minz+",Help,undefined,undefined);
        this.addCommand("changeBoxCommand5","Box Size",0,"shift+s","minz-",Help,undefined,undefined);
        this.addCommand("changeBoxCommand6","Box Size",0,"shift+d","maxx+",Help,undefined,undefined);
        this.addCommand("changeBoxCommand7","Box Size",0,"shift+f","maxx-",Help,undefined,undefined);
        this.addCommand("changeBoxCommand8","Box Size",0,"shift+z","maxy+",Help,undefined,undefined);
        this.addCommand("changeBoxCommand9","Box Size",0,"shift+x","maxy-",Help,undefined,undefined);
        this.addCommand("changeBoxCommand10","Box Size",0,"shift+c","maxz+",Help,undefined,undefined);
        this.addCommand("changeBoxCommand11","Box Size",0,"shift+v","maxz-",Help,undefined,undefined);

        this.addCommand("changeBoxArrowL","Box Dir",0,"shift+left","positive",ArrowLeft,undefined,undefined);
        this.addCommand("changeBoxArrowR","Box Dir",0,"shift+right","negative",ArrowRight,undefined,undefined);

        this.addCommand("viewPicCommand","Picture View ",0,"P","viewpicture",ViewColumn,undefined,undefined);

        this.addCommand("CleanCommand","Clean Tab",0,"C","clean",Refresh,undefined,undefined);

        this.addCommand("pageUpCommand","Page Up",0,"A","pageup",ArrowLeft,undefined,undefined);
        this.addCommand("pageDownCommand","Page Down",0,"D","pagedown",ArrowRight,undefined,undefined);

        this.addCommand("selectorCommand", "Lasso Selector", 1, "H", "selector", Gesture, undefined, undefined);
        this.addCommand("rectangleCommand", "Rectangle Selector", 1, "J", "rectangle", SquareOutline, undefined, undefined);
        this.addCommand("circleCommand", "Circle Selector", 1, "K", "circle", CircleOutline, undefined, undefined);

        this.addCommand("selectionAddCommand", "Selection Mode: Add", 2, "Y", "selection-mode-add", Plus, undefined, undefined);
        this.addCommand("selectionToggleCommand", "Selection Mode: Toggle", 2, "U", "selection-mode-toggle", PlusMinus, undefined, undefined);
        this.addCommand("selectionRemoveCommand", "Selection Mode: Remove", 2, "I", "selection-mode-remove", Minus, undefined, undefined);

        this.addCommand("moreClusterCommand", "More Cluster", false, "ctrl+up", "cluster-more", Plus, undefined, "Ctrl \u2191");
        this.addCommand("lessClusterCommand", "Less Cluster", false, "ctrl+down", "cluster-less", Minus, undefined, "Ctrl \u2193");


        this.addCommand("autoFilterCommand", "Auto Filter", false, "L", "autoFilter-checkbox");
        this.addCommand("autoFocusCommand", "Auto Focus", false, "S", "autoFocus-checkbox");
        this.addCommand("globalboxCommand", "Bounding Box", false, "G", "globalbox-checkbox");
        this.addCommand("selectionOutlineCommand", "Selection Outline", false, "V", "selectionOutline-checkbox");

        this.addCommand("undoCommand", "Undo", false, "Ctrl+Z", "undo", Undo, undefined,undefined);
        this.addCommand("redoCommand", "Redo", false, "Ctrl+Y", "redo", Redo, "disabled");
        this.addCommand("downloadTextCommand", "PCD Output as Text", false, "", "downloadText", FileDownloadOutline);
        this.addCommand("downloadFileCommand", "PCD Output as File", false, "", "downloadFile", FileDownloadOutline);
        this.addCommand("downloadBoxCommand", "BOX Output as File", false, "", "downloadBox", FileDownloadOutline);

        this.sendMsg("selector");
        this.sendMsg("selection-mode-add");
    }

    render() {
        return (
            <div className="hflex flex-justify-content-space-around sse-toolbar toolbar-3d no-shrink">
                <SseBranding/>

                <div  class="dropdown">
                <button class="dropbtn">change boxsize</button>
                <div class="dropdown-content">
                    <li>   {this.renderMyCommand("changeBoxCommand0")}  </li>
                    <li>   {this.renderMyCommand("changeBoxCommand1")}  </li>
                    <li>   {this.renderMyCommand("changeBoxCommand2")}  </li>
                    <li>   {this.renderMyCommand("changeBoxCommand3")}  </li>
                    <li>   {this.renderMyCommand("changeBoxCommand4")}  </li>
                    <li>   {this.renderMyCommand("changeBoxCommand5")}  </li>
                    <li>   {this.renderMyCommand("changeBoxCommand6")}  </li>
                    <li>   {this.renderMyCommand("changeBoxCommand7")}  </li>
                    <li>   {this.renderMyCommand("changeBoxCommand8")}  </li>
                    <li>   {this.renderMyCommand("changeBoxCommand9")}  </li>
                    <li>   {this.renderMyCommand("changeBoxCommand10")}  </li>
                    <li>   {this.renderMyCommand("changeBoxCommand11")}  </li>
                </div>
                </div>

                <div  class="dropdown">
                <button class="dropbtn">change boxdir</button>
                <div class="dropdown-content">
                    <li>   {this.renderMyCommand("changeBoxArrowL")}  </li>
                    <li>   {this.renderMyCommand("changeBoxArrowR")}  </li>
                </div>
                </div>

                <div className="vflex">
                    <div className="tool-title">Return Folder</div>
                    <div className="hflex">
                    {this.renderCommand("returnFolderCommand")}
                    </div>
                </div>

                <div className="vflex">
                    <div className="tool-title">Picture View</div>
                    <div className="hflex">
                    {this.renderCommand("viewPicCommand")}
                    </div>
                </div>

                <div className="vflex">
                    <div className="tool-title">Clean Tab</div>
                    <div className="hflex">
                    {this.renderCommand("CleanCommand")}
                    </div>
                </div>

                <div className="vflex">
                    <div className="tool-title">Undo</div>
                    <div className="hflex">
                    {this.renderCommand("undoCommand")}
                    </div>
                </div>

                <div className="vflex">
                    <div className="tool-title">Page Change</div>
                    <div className="hflex">
                    {this.renderCommand("pageUpCommand")}
                    {this.renderCommand("pageDownCommand")}
                    </div>
                </div>

                <div className="vflex">
                    <div className="tool-title">Selection Tool</div>
                    <div className="hflex">
                        {this.renderCommand("selectorCommand")}
                        {this.renderCommand("rectangleCommand")}
                        {this.renderCommand("circleCommand")}
                    </div>
                </div>
                <div className="vflex">
                    <div className="tool-title">Selection Mode</div>
                    <div className="hflex">
                        {this.renderCommand("selectionAddCommand")}
                        {this.renderCommand("selectionToggleCommand")}
                        {this.renderCommand("selectionRemoveCommand")}
                    </div>
                </div>
                <div className="vflex">
                    <div className="tool-title">View Interaction</div>
                    <div className="v group">
                        {this.renderCheckbox("autoFocusCommand", false)}
                        {this.renderCheckbox("autoFilterCommand", false)}
                    </div>
                </div>
                <div className="vflex">
                    <div className="tool-title">Visual Helpers</div>
                    <div className="v group">
                        {this.renderCheckbox("selectionOutlineCommand", true)}
                        {this.renderCheckbox("globalboxCommand", true)}
                    </div>
                </div>
                <div className="vflex">
                    <div className="tool-title">PCD Output</div>
                    <div className="hflex">
                        {this.renderCommand("downloadTextCommand")}
                        {this.renderCommand("downloadFileCommand")}
                        {this.renderCommand("downloadBoxCommand")}
                    </div>
                </div>
            </div>
        )
    }
}