//
//  ViewController.swift
//  checkROCID
//
//  Created by 申潤五 on 2022/9/7.
//

import UIKit

class ViewController: UIViewController {
    @IBOutlet weak var id: UITextField!
    
    @IBOutlet weak var result: UILabel!
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }

    @IBAction func checkAction(_ sender: Any) {
        let idString = id.text ?? ""
        result.text = "檢查結果是否符合：\(idString.checkIdFormat())"
    }
    
}

extension String{
    func checkIdFormat(includeRC:Bool=true)->Bool{
        let uppercasd = self.uppercased()
        //長度必需為 10 碼
        if uppercasd.count != 10{
            return false
        }
        //第一個字母必需為 A-Z
        let first = String(uppercasd[uppercasd.index(uppercasd.startIndex, offsetBy: 0)..<uppercasd.index(uppercasd.startIndex, offsetBy: 1)])
        if first.range(of: "[A-Z]",options:  .regularExpression) == nil {
            return false
        }
        //第二個字母身份證必需為 1,2 居留證也可以是 1,2,A,B,C,D,8,9
        let second = String(uppercasd[uppercasd.index(uppercasd.startIndex, offsetBy: 1)..<uppercasd.index(uppercasd.startIndex, offsetBy: 2)])
        if second.range(of: includeRC ? "[12ABCD89]" : "[12]",options: .regularExpression) == nil {
            return false
        }
        //第三到十碼必需為數字
        let idNumbers = String(uppercasd[uppercasd.index(uppercasd.startIndex, offsetBy: 2)..<uppercasd.index(uppercasd.startIndex, offsetBy: 10)])
        if idNumbers.range(of: "[0-9]{8}",options: .regularExpression) == nil {
            return false
        }
        //計算檢查碼
        let idReplaceChat = ["A":"10","B":"11","C":"12","D":"13","E":"14",
                             "F":"15","G":"16","H":"17","I":"34","J":"18",
                             "K":"19","L":"20","M":"21","N":"22","O":"35",
                             "P":"23","Q":"24","R":"25","S":"26","T":"27",
                             "U":"28","V":"29","W":"32","X":"30","Y":"31",
                             "Z":"33"]
        let genderReplaceChat = ["1":"1","2":"2","A":"1","B":"2","C":"1","D":"2","8":"8","9":"9"]
        let weightChat = [1,9,8,7,6,5,4,3,2,1,1]
        let checkBaseString = (idReplaceChat[first] ?? "") + (genderReplaceChat[second] ?? "") + idNumbers
        var i = 0
        for index in 0...10{
            String(checkBaseString[checkBaseString.index(checkBaseString.startIndex, offsetBy: index)..<checkBaseString.index(checkBaseString.startIndex, offsetBy: index + 1)])
            if let number = Int(String(checkBaseString[checkBaseString.index(checkBaseString.startIndex, offsetBy: index)..<checkBaseString.index(checkBaseString.startIndex, offsetBy: index + 1)])){
                i += (number*weightChat[index])%10
            }
        }
        if ( i % 10 ) != 0 {
            return false
        }
        return true
    }
}

