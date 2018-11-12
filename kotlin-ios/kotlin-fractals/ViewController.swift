//
//  ViewController.swift
//  kotlin-fractals
//
//  Created by Evgeny Petrenko on 12.11.18.
//  Copyright © 2018 Evgeny Petrenko. All rights reserved.
//

import UIKit
import common

class ViewController: UIViewController {
    @IBOutlet weak var myImage: UIImageView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
    
        let ciImage = TheRenderer().render(width: 200,
                                           height: 400)
        myImage.image = UIImage(ciImage: ciImage)
    }
    
    
}

