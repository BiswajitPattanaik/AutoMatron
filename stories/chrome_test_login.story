Scenario:
Given a chrometest with testcaseName firstTest
And open Netstorm ProductUI for http://10.10.30.106
When login with username <username> and password <userpass> for TestLogin
Then home page login name should be <username> for casename LoginStepValidation
Given create a scenario with name <scenario_name> project <project_name> subproject <sub_project_name> casename CreateScenarioTest
And add group with groupname <groupname> , script <script_name> , project <project_name> , subproject <sub_project_name> , casename AddGroup
Then quit chrometest


Examples:

