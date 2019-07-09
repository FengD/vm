import Sse3dSelector from "./Sse3dSelector";

export default class extends Sse3dSelector {
    mouseDown(ev) {
        if (ev.which == 3) {
            this.pushPoint(ev.offsetX, ev.offsetY);
        }
    }

    mouseUp(ev) {
        if (ev.which == 3) {
            this.scene.selectByPolygon(this.polygon);
            this.polygon.length = 0;
            // console.log(this.scene.selectByPolygon(this.polygon));
            // console.log("scene",this.scene);
            //before change store set include points which will be changed
            console.log("mouse_up_type",typeof(this.scene.selection));
            var temp=[];
            for (var x of this.scene.selection) { // 遍历Array
                temp.push(x);
            }
            var str=JSON.stringify(temp);
            localStorage.setItem("changepoints",str);
        }
    }

    mouseDrag(ev) {
        if (ev.which == 3) {
            this.pushPoint(ev.offsetX, ev.offsetY);
        }
    }


}