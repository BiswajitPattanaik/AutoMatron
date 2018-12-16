import xml.etree.ElementTree as ET

control_structure={}
with open('./controls/Models.txt','r') as f:
    application_structure={}
    for line in f:
        tree=ET.parse('./controls/xml/{}.xml'.format(line.strip()))
        root=tree.getroot()
        page=root.find(line.strip())
        page_structure={}
        for element in page:
            page_structure[element.tag]=element.attrib
        application_structure[line.strip()]=page_structure
    control_structure['Application']=application_structure
print control_structure
