import Sse3dSelector from "./Sse3dSelector";

export default class extends Sse3dSelector {

    mouseDown(ev) {
        if (ev.which == 3) {
            this.pushPoint(ev.offsetX, ev.offsetY);
            this.startX = ev.offsetX;
            this.startY = ev.offsetY;
        }
    }

    mouseUp(ev) {
        if (ev.which == 3) {
            this.scene.selectByPolygon(this.polygon);
            this.polygon.length = 0;
            this.startX = this.startY = NaN;
            //before change store set include points which will be changed
            var temp=[];
            for (var x of this.scene.selection) { // 遍历Array
                temp.push(x);
            }
            var str=JSON.stringify(temp);
            localStorage.setItem("changepoints",str);
        }
    }

    pushPoint(x, y) {
        super.pushPoint(x + .5, y + .5);
    }

    mouseDrag(ev) {
        if (ev.which == 3) {
            this.polygon.length = 0;
            const fx = this.startX;
            const fy = this.startY;
            this.pushPoint(fx, fy);
            this.pushPoint(ev.offsetX, fy);
            this.pushPoint(ev.offsetX, ev.offsetY);
            this.pushPoint(fx, ev.offsetY);
            this.pushPoint(fx, fy);
        }
    }
}