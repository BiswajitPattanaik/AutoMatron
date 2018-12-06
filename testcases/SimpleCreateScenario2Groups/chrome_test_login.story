Scenario:
Given a chrometest with testcaseName firstTest
And open Netstorm ProductUI for http://10.10.30.106
When login with username <username> and password <userpass>
Then home page login name should be <username>
Given create a scenario with name <scenario_name> project <project_name> subproject <sub_project_name>
Then add group with groupname <groupname> , script <script_name> , project <project_name> , subproject <sub_project_name>
Then add group with groupname <groupname2> , script <script_name> , project <project_name> , subproject <sub_project_name>
And save the scenario
And move to TotalScenarios UI
Then quit chrometest


Examples:
chrome_test_login1.table
