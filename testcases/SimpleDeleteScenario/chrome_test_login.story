Scenario:
Given a chrometest with testcaseName firstTest
And open Netstorm ProductUI for http://10.10.30.106
When login with username <username> and password <userpass>
Then home page login name should be <username>
Then open total scenarios UI
And delete scenario <scenario_name>
Then quit chrometest


Examples:
chrome_test_login1.table
