param($xmlpath, $browser, $testPlans, $env, $thread)

function Edit-XmlNodes 
{ #This function is used to update the given XML node value using node xpath
    param
    (
        [xml] $xmldoc = $(throw "xmldoc is a required parameter"),
        [string] $xpath = $(throw "xpath is a required parameter"),
        [string] $value = $(throw "value is a required parameter")
    )
        Try
        {
            $nodes = $xmldoc.SelectNodes($xpath)
             foreach ($node in $nodes) {
                        $node.Value = $value
             }
        }
        Catch 
        {
            $ErrorMessage = $_.Exception.Message
            Write-host "Exception" $ErrorMessage
        }
}

function Enable-Tests 
{ #This function is used to enable the tests in given XML using node xpath
    param
    (
        [xml] $xmldoc = $(throw "xmldoc is a required parameter"),
        [string] $xpathTestName = $(throw "xpathTestName is a required parameter"),
        [string] $planName = $(throw "planName is a required parameter")
    )
        Try
        {
           $nodes = $xml.SelectNodes($xpathTestName)
           foreach ($node in $nodes){
               if ($node.Value -like '*'+$planName){
                   $testxpath = "/suite/test[@name='"+$node.Value+"']/@enabled"
                   $tests = $xml.SelectNodes($testxpath)
                   foreach ($test in $tests){
                      $test.Value= "true"
                   }
               }
           }
            
        }
        Catch 
        {
            $ErrorMessage = $_.Exception.Message
            Write-host "Exception" $ErrorMessage
        }
}

Try
{
	$xpathSuite = "/suite/@name"
    $xpathEnable = "/suite/test/@enabled"
    $xpathTestNames = "/suite/test/@name"
    $xpathbrowserName = "/suite/parameter[@name='browserName']/@value"
    $xpaththreadcount = "/suite/test/@thread-count"
    $suitName = "HOOD_" + $env;
	
    [string[]]$plans = $testPlans -split ","
    write-host "Plans Selected are :"$testPlans
    
    If ($browser -like 'Internet*' )
    {
        $browser = "iexplorer"
		$browserOS = "iexplorer_windows"
    }
    
    ElseIf ($browser -like 'Chrome*' )
    {
        $browser = "chrome"
		$browserOS = "chrome_windows"
    }
    
    ElseIf ($browser -like 'Firefox*' )
    {
        $browser = "firefox"
		$browserOS = "firefox_windows"
    }
    
    $xml = [xml](Get-Content $xmlpath) #Gets the XML Content in $xml parameter
    
    Edit-XmlNodes $xml -xpath $xpathSuite -value $suitName #Calls the Edit-XmlNodes function to update the Suite Name the given XML
    write-host "Updated Suite Name:" $suitName "in Xml"
             
	Edit-XmlNodes $xml -xpath $xpathbrowserName -value $browserOS #Calls the Edit-XmlNodes function to update the browserName parameter value in the given XML
    write-host "Updated Browser Name and OS Info:" $browserOS "in Xml"
    
	Edit-XmlNodes $xml -xpath $xpathEnable -value "false" #Calls the Edit-XmlNodes function to disable all the tests in the given XML
    write-host "Disabled all the tests in XML"
    
    Foreach ($plan in $plans)
    {		
		If ($env -like 'SystemTest*' )
		{
			$webSite = "http://aldvmstweb02.siriusfs.com/"
		}
		ElseIf ($env -like 'FOF_ST' )
		{
			$webSite = "http://aldvmstweb02.siriusfs.com/"
		}
		ElseIf ($env -like 'SIT*' )
		{
			$webSite = "https://hood-sit.ssp-hosting.com/"
		}
		ElseIf ($env -like 'FOF_SIT' )
		{
			$webSite = "https://hood-sit2.ssp-hosting.com/"
		}
		ElseIf ($env -like 'UAT*' )
		{
			$webSite = "https://hood-test.ssp-hosting.com/"
		}
		ElseIf ($env -like 'PrePROD*' )
		{
			$webSite = "https://hood-test.ssp-hosting.com/HG"
		}
		ElseIf ($env -like 'Training*' )
		{
			$webSite = "https://hood-training.ssp-hosting.com/"
		}
		ElseIf ($env -like 'DR*' )
		{
			$webSite = "https://dr-hood-prod.ssp-hosting.com/"
		}
		
		$xpathTestNames = "/suite/test[@name='" + $plan + "']"
			
		If ($plan -like 'WJ*' )
		{
			$webSite = $webSite + "HomeInsurance"
			
			If ($env -like 'FOF*' )
			{
				$webSite = $webSite + "FOF"
			}			
		}
		ElseIf ($plan -like 'EC*' )
		{
			$webSite = $webSite + "SIaaS"
			
			If ($env -like 'FOF*' )
			{
				$webSite = $webSite + "FOF"
			}	
		}
		ElseIf ($plan -like 'SS*' )
		{
			$webSite = $webSite + "SIaaS-SSC"
			
			If ($env -like 'FOF*' )
			{
				$webSite = $webSite + "FOF"
			}	
		}
				
		$xpaththreadcount = $xpathTestNames + "/@thread-count"
		write-host "Updated xpaththreadcount:" $xpaththreadcount "in Xml"
		$xpathWeb = $xpathTestNames + "/parameter[@name='webSite']/@value"
		write-host "Updated xpathWeb:" $xpathWeb "in Xml"
		$xpathEnv = $xpathTestNames + "/parameter[@name='env']/@value"
		write-host "Updated xpathEnv:" $xpathEnv "in Xml"
		$xpathdriverType = $xpathTestNames + "/parameter[@name='browser']/@value"
		write-host "Updated xpathdriverType:" $xpathdriverType "in Xml"
		
		Edit-XmlNodes $xml -xpath $xpaththreadcount -value $thread #Calls the Edit-XmlNodes function to update the thread count value in the given XML
		write-host "Updated Thread Count:" $thread "in Xml"

		$xpathTestNames = "/suite/test/@name"
    
		Enable-Tests $xml -xpathTestName $xpathTestNames -planName $plan #Calls the Enable-Tests function to enable the given test in the given XML
		write-host "Enabled test:" $plan "in Xml"     
		
		Edit-XmlNodes $xml -xpath $xpathWeb -value $webSite #Calls the Edit-XmlNodes function to update the WebSite parameter value in the given XML
		write-host "Updated WebSite URL:" $webSite "in Xml"
		
		Edit-XmlNodes $xml -xpath $xpathEnv -value $env #Calls the Edit-XmlNodes function to update the Environment parameter value in the given XML
		write-host "Updated Environment:" $env "in Xml"
		
		Edit-XmlNodes $xml -xpath $xpathdriverType -value $browser #Calls the Edit-XmlNodes function to update the Browser parameter value in the given XML
		write-host "Updated driverType:" $browser "in Xml"
    	
	}   	
	$xml.Save($xmlpath)
	write-host "XML Saved Successfully"
    	
}
Catch {
    $ErrorMessage = $_.Exception.Message
    Write-host "Exception" $ErrorMessage
}