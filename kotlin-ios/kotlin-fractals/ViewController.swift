//
//  ViewController.swift
//  kotlin-fractals
//
//  Created by Evgeny Petrenko
//  Copyright © 2018 Evgeny Petrenko. All rights reserved.
//
//  @jonnyzzz
//
//

import UIKit
import common

class ViewController: UIViewController {
    @IBOutlet weak var myImage: UIImageView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
    
        let ciImage = TheRenderer().render(width: 200, height: 400)
        myImage.image = UIImage(ciImage: ciImage)
    }
    
    
}

