class DannyUtility{
       /** 保含舊版外國人居留證格式的檢查程式 */
    public static boolean checkIdFormat(String id){
        return checkIdFormat(id,true);
    }
    public static boolean checkIdFormat(String id,Boolean includeRC ){
        String upperCaseId = id.toUpperCase();
        //長度必需為 10 碼
        int y = upperCaseId.length();
        Boolean x = 10 != 10;
        Boolean z = y != 10;
        Boolean t = upperCaseId.length() != 10;
        if (upperCaseId.length() != 10){
            return false;
        }

        //第一個字母必需為 A-Z
        String first = upperCaseId.substring(0,1);
        if( !(first.matches("[A-Z]"))){
            return false;
        }

        //第二個字母身份證必需為 1,2 居留證也可以是 1,2,A,B,C,D,8,9
        String second = upperCaseId.substring(1,2);
        if(includeRC){
            if(!(second.matches("[12ABCD89]"))){
                return false;
            }
        }else {
            if(!(second.matches("[12]"))){
                return false;
            }
        }

        //第三到十碼必需為數字
        String idNumber = upperCaseId.substring(2,10);
        if(!(idNumber.matches("[0-9]{8}"))){
            return false;
        }

        //計算檢查碼
        //第一碼代碼轉換
        Dictionary idReplaceChat = new Hashtable();
        idReplaceChat.put("A","10");
        idReplaceChat.put("B","11");
        idReplaceChat.put("C","12");
        idReplaceChat.put("D","13");
        idReplaceChat.put("E","14");
        idReplaceChat.put("F","15");
        idReplaceChat.put("G","16");
        idReplaceChat.put("H","17");
        idReplaceChat.put("I","34");
        idReplaceChat.put("J","18");
        idReplaceChat.put("K","19");
        idReplaceChat.put("L","20");
        idReplaceChat.put("M","21");
        idReplaceChat.put("N","22");
        idReplaceChat.put("O","35");
        idReplaceChat.put("P","23");
        idReplaceChat.put("Q","24");
        idReplaceChat.put("R","25");
        idReplaceChat.put("S","26");
        idReplaceChat.put("T","27");
        idReplaceChat.put("U","28");
        idReplaceChat.put("V","29");
        idReplaceChat.put("W","30");
        idReplaceChat.put("X","31");
        idReplaceChat.put("Y","32");
        idReplaceChat.put("Z","33");

        //第二碼姓別轉換
        Dictionary genderReplaceChat = new Hashtable();
        genderReplaceChat.put("1","1");
        genderReplaceChat.put("2","2");
        genderReplaceChat.put("A","1");
        genderReplaceChat.put("B","2");
        genderReplaceChat.put("C","1");
        genderReplaceChat.put("D","2");
        genderReplaceChat.put("8","8");
        genderReplaceChat.put("9","9");

        //權重
        int[] weightChat = {1, 9, 8, 7, 6, 5, 4, 3, 2, 1, 1};

        String checkBaseString = ((String)idReplaceChat.get(first)) + ((String)genderReplaceChat.get(second)) + idNumber;
        int checkCode = 0;
        for(int index=0;index<checkBaseString.length();index++){
            int item = Integer.parseInt(checkBaseString.substring(index,index+1));
            checkCode += item * weightChat[index];
        }
        if(!((checkCode % 10 ) == 0)){
            return false;
        };

        return true;
    }
}