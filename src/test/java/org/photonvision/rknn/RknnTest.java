/*
 * Copyright (C) Photon Vision.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.photonvision.rknn;

import java.io.IOException;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.dnn.Dnn;
import org.opencv.imgcodecs.Imgcodecs;

public class RknnTest {
    public static void main(String[] args) throws IOException {
        System.load("/home/matt/Documents/GitHub/photonvision/photon-targeting/build/NativeMain/RawRuntimeLibs/linux/x86-64/shared/libopencv_java480.so");
        System.load("/home/matt/Documents/rknn/rknn_java/cmake_build/librknn_java.so");

        Mat img = Imgcodecs.imread("src/test/resources/bus.jpg");
        System.out.println(img);

        var blob = Dnn.blobFromImage(img, 1.0 / 255.0, new Size(640, 640));

        long ptr = RknnJNI.create("/home/matt/Documents/rknn/rknn_java/src/test/resources/yolov7-tiny.onnx");
        System.out.println(ptr);
        var ret = RknnJNI.detect(ptr, blob.getNativeObjAddr(), 0, 0, 1);
        System.out.println(ret);
        RknnJNI.destroy(ptr);

        System.out.println(blob);

        img.release();
        blob.release();
    }
}