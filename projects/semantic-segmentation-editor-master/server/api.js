import SseDataWorkerServer from "./SseDataWorkerServer";
import configurationFile from "./config";
import { basename } from "path";
import { readFile } from "fs";
import * as THREE from 'three';
import SsePCDLoader from "../imports/editor/3d/SsePCDLoader";

WebApp.connectHandlers.use("/api/json", generateJson);
WebApp.connectHandlers.use("/api/pcdtext", generatePCDOutput.bind({ fileMode: false }));
WebApp.connectHandlers.use("/api/pcdfile", generatePCDOutput.bind({ fileMode: true }));
WebApp.connectHandlers.use("/api/boxfile", generateBOXOutput.bind({ fileMode: true }));
WebApp.connectHandlers.use("/api/listing", imagesListing);

WebApp.connectHandlers.use("/api/allpcdfile", generateAllPCDOutput.bind({ fileMode: true }));

const { imagesFolder, pointcloudsFolder, setsOfClassesMap } = configurationFile;
new SsePCDLoader(THREE);

function imagesListing(req, res, next) {
    const all = SseSamples.find({}, {
        fields: {
            url: 1,
            folder: 1,
            file: 1,
            tags: 1,
            firstEditDate: 1,
            lastEditDate: 1
        }
    }).fetch();
    res.end(JSON.stringify(all, null, 1));
}

function generateJson(req, res, next) {
    res.setHeader('Content-Type', 'application/json');
    const item = SseSamples.findOne({ url: req.url });
    if (item) {
        const soc = setsOfClassesMap.get(item.socName);
        item.objects.forEach(obj => {
            obj.label = soc.objects[obj.classIndex].label;
        });
        res.end(JSON.stringify(item, null, 1));
    } else {
        res.end("{}");
    }
}

function generateAllPCDOutput(req, res, next) {
    var test_url = req.url.split(",");
    var empty_url = []

    testUrls(test_url, 0, test_url.length - 1, empty_url, res);
    var out = "";
    // readFiles(temp_url, 0, temp_url.length - 1, out, res);
}

function testUrls(urls, index, endIndex, final_urls, res) {
    var labelFile = pointcloudsFolder + decodeURIComponent(urls[index]) + ".labels";
    readFile(labelFile, (labelErr, labelContent) => {
        if (labelContent != null) {
            final_urls.push(urls[index]);
            // console.log("final_urls_process", final_urls);
        }
        if (index != endIndex) {
            index = index + 1;
            testUrls(urls, index, endIndex, final_urls, res);
        } else {
            // console.log("final_urls", final_urls);
            // return final_urls;
            var out = "";
            readFiles(final_urls, 0, final_urls.length - 1, out, res);
        }
    });
}

function readFiles(files, index, endIndex, out, res) {
    var pcdFile = imagesFolder + decodeURIComponent(files[index]);
    var fileName = basename(pcdFile);
    var labelFile = pointcloudsFolder + decodeURIComponent(files[index]) + ".labels";
    var objectFile = pointcloudsFolder + decodeURIComponent(files[index]) + ".objects";
    if (this.fileMode) {
        res.setHeader('Content-disposition', 'attachment; filename=DOC'.replace("DOC", fileName));
        res.setHeader('Content-type', 'text/plain');
        res.charset = 'UTF-8';
    }
    readFile(pcdFile, (err, content) => {
        if (err) {
            res.end("Error while parsing PCD file.")
        }

        const loader = new THREE.PCDLoader(true);
        const pcdContent = loader.parse(content.toString(), "");

        const head = pcdContent.header;

        let out = fileName + "\n";
        out += "VERSION .7\n";
        out += "FIELDS x y z label object\n";
        out += "SIZE 4 4 4 4 4\n";
        out += "TYPE F F F I I\n";
        out += "COUNT 1 1 1 1 1\n";
        out += "WIDTH " + pcdContent.position.length + "\n";
        out += "HEIGHT 1\n";
        out += "POINTS " + pcdContent.position.length + "\n";
        out += "VIEWPOINT " + head.viewpoint.tx;
        out += " " + head.viewpoint.ty;
        out += " " + head.viewpoint.tz;
        out += " " + head.viewpoint.qw;
        out += " " + head.viewpoint.qx;
        out += " " + head.viewpoint.qy;
        out += " " + head.viewpoint.qz + "\n";
        out += "DATA ascii\n";
        res.write(out);
        out = "";
        readFile(labelFile, (labelErr, labelContent) => {
            if (labelErr) {
                res.end("Error while parsing labels file.")
            }
            const labels = SseDataWorkerServer.uncompress(labelContent);
            // console.log("labels", labels);
            readFile(objectFile, (objectErr, objectContent) => {
                let objectsAvailable = true;
                if (objectErr) {
                    objectsAvailable = false;
                }

                const objectByPointIndex = new Map();

                if (objectsAvailable) {
                    const objects = SseDataWorkerServer.uncompress(objectContent);
                    // console.log("objects", objects);
                    objects.forEach((obj, objIndex) => {
                        obj.points.forEach(ptIdx => {
                            objectByPointIndex.set(ptIdx, objIndex);
                        })
                    });
                }
                let obj;
                // console.log("pcdContent", pcdContent);
                pcdContent.position.forEach((v, i) => {
                    const position = Math.floor(i / 3);

                    switch (i % 3) {
                        case 0:
                            obj = { x: v };
                            break;
                        case 1:
                            obj.y = v;
                            break;
                        case 2:
                            obj.z = v;
                            out += obj.x + " " + obj.y + " " + obj.z + " ";
                            out += labels[position] + " ";
                            const assignedObject = objectByPointIndex.get(position);
                            if (assignedObject != undefined)
                                out += assignedObject;
                            else
                                out += "-1";
                            out += "\n";
                            res.write(out);
                            out = "";
                            break;
                    }
                });
                if (index != endIndex) {
                    index = index + 1;
                    readFiles(files, index, endIndex, out, res);
                } else {
                    res.end();
                }
            });
        });
    });
}

function generatePCDOutput(req, res, next) {
    const pcdFile = imagesFolder + decodeURIComponent(req.url);
    const fileName = basename(pcdFile);
    const labelFile = pointcloudsFolder + decodeURIComponent(req.url) + ".labels";
    const objectFile = pointcloudsFolder + decodeURIComponent(req.url) + ".objects";
    // console.log("req", req);
    // console.log("req.url", req.url);
    // console.log("res", res);
    // console.log("pcdFile", pcdFile);
    // console.log("fileName", fileName);
    // console.log("labelFile", labelFile);
    // console.log("objectFile", objectFile);

    if (this.fileMode) {
        res.setHeader('Content-disposition', 'attachment; filename=DOC'.replace("DOC", fileName));
        res.setHeader('Content-type', 'text/plain');
        res.charset = 'UTF-8';
    }

    readFile(pcdFile, (err, content) => {
        if (err) {
            res.end("Error while parsing PCD file.")
        }

        const loader = new THREE.PCDLoader(true);
        const pcdContent = loader.parse(content.toString(), "");

        const head = pcdContent.header;

        let out = "VERSION .7\n";
        out += "FIELDS x y z label object\n";
        out += "SIZE 4 4 4 4 4\n";
        out += "TYPE F F F I I\n";
        out += "COUNT 1 1 1 1 1\n";
        out += "WIDTH " + pcdContent.position.length + "\n";
        out += "HEIGHT 1\n";
        out += "POINTS " + pcdContent.position.length + "\n";
        out += "VIEWPOINT " + head.viewpoint.tx;
        out += " " + head.viewpoint.ty;
        out += " " + head.viewpoint.tz;
        out += " " + head.viewpoint.qw;
        out += " " + head.viewpoint.qx;
        out += " " + head.viewpoint.qy;
        out += " " + head.viewpoint.qz + "\n";
        out += "DATA ascii\n";
        res.write(out);
        out = "";
        readFile(labelFile, (labelErr, labelContent) => {
            if (labelErr) {
                res.end("Error while parsing labels file.")
            }
            const labels = SseDataWorkerServer.uncompress(labelContent);
            // console.log("labels", labels);

            readFile(objectFile, (objectErr, objectContent) => {
                let objectsAvailable = true;
                if (objectErr) {
                    objectsAvailable = false;
                }

                const objectByPointIndex = new Map();

                if (objectsAvailable) {
                    const objects = SseDataWorkerServer.uncompress(objectContent);
                    // console.log("objects", objects);
                    objects.forEach((obj, objIndex) => {
                        obj.points.forEach(ptIdx => {
                            objectByPointIndex.set(ptIdx, objIndex);
                        })
                    });
                }
                let obj;
                // console.log("pcdContent", pcdContent);
                pcdContent.position.forEach((v, i) => {
                    const position = Math.floor(i / 3);

                    switch (i % 3) {
                        case 0:
                            obj = { x: v };
                            break;
                        case 1:
                            obj.y = v;
                            break;
                        case 2:
                            obj.z = v;
                            out += obj.x + " " + obj.y + " " + obj.z + " ";
                            out += labels[position] + " ";
                            const assignedObject = objectByPointIndex.get(position);
                            if (assignedObject != undefined)
                                out += assignedObject;
                            else
                                out += "-1";
                            out += "\n";
                            res.write(out);
                            out = "";
                            break;
                    }
                });

                res.end();
            })
        });
    });
}

function generateBOXOutput(req, res, next) {
    const pcdFile = imagesFolder + decodeURIComponent(req.url);
    const fileName = basename(pcdFile).slice(0, -3) + "box";
    const objectFile = pointcloudsFolder + decodeURIComponent(req.url) + ".objects";

    if (this.fileMode) {
        res.setHeader('Content-disposition', 'attachment; filename=DOC'.replace("DOC", fileName));
        res.setHeader('Content-type', 'text/plain');
        res.charset = 'UTF-8';
    }

    readFile(pcdFile, (err, content) => {
        if (err) {
            res.end("Error while parsing PCD file.");
        }

        let out = "VERSION .7\n";
        out += "FIELDS objIndex length width height x y z angel classindex\n";
        out += "DATA ascii\n";
        res.write(out);
        out = "";
        readFile(objectFile, (objectErr, objectContent) => {
            let objectsAvailable = true;
            if (objectErr) {
                objectsAvailable = false;
            }

            if (objectsAvailable) {
                const objects = SseDataWorkerServer.uncompress(objectContent);
                objects.forEach((obj, objIndex) => {
                    // console.log("obj-index",objIndex,obj.min,obj.max,obj.angle,obj.classIndex);
                    out += objIndex + " " +
                        obj.length + " " + obj.width + " " + obj.height + " " +
                        obj.centre.x + " " + obj.centre.y + " " + obj.centre.z + " " +
                        obj.angle * Math.PI / 180 + " " + obj.classIndex + "\n";
                    // out +=objIndex + " " + 
                    // obj.min.x + " " + obj.min.y + " " +obj.min.z + " " +
                    // obj.max.x + " " + obj.max.y + " " +obj.max.z + " " +
                    // obj.angle + " " + obj.classIndex + "\n";
                });
                res.write(out);
            }
            res.end();
        })
    });
}