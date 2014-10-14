//
//  ViewController.swift
//  sampleCoreMotion
//
//  Created by Makoto TOBITA on 10/10/14.
//  Copyright (c) 2014 DSK Inc. All rights reserved.
//

import UIKit
import CoreMotion

class ViewController: UIViewController {
    let motionManager:CMMotionManager = CMMotionManager()
    
    @IBOutlet weak var startButton: UIButton!
    @IBOutlet weak var stopButton: UIButton!
    @IBOutlet weak var xLabel: UILabel!
    @IBOutlet weak var yLabel: UILabel!
    @IBOutlet weak var zLabel: UILabel!

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        self.motionManager.accelerometerUpdateInterval = 0.5
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func start(sender: AnyObject) {
        println("start")
        self.motionManager.startAccelerometerUpdatesToQueue(NSOperationQueue.currentQueue(), withHandler: {
            (accelerometerData:CMAccelerometerData!, error : NSError!) in
            println("update")
            println("x: \(accelerometerData.acceleration.x)")
            println("y: \(accelerometerData.acceleration.y)")
            println("z: \(accelerometerData.acceleration.z)")
        })
    }
    
    @IBAction func stop(sender: AnyObject) {
        println("stop")
        self.motionManager.stopAccelerometerUpdates()
    }
}

