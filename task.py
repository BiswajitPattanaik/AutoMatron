from flask import Flask, render_template, request, jsonify, session, redirect, url_for
import xml.etree.ElementTree as ET
app = Flask(__name__)

control_structure={}
@app.route('/',methods= ['GET'])
def index():
	with open('./controls/Models.txt','r') as f:
		application_structure={}
		page_list=[]
		for line in f:
			tree=ET.parse('./controls/xml/{}.xml'.format(line.strip()))
			root=tree.getroot()
			page=root.find(line.strip())
			page_structure={}
			for element in page:
				page_structure[element.tag]=element.attrib
			page_list.append(line.strip())
			application_structure[line.strip()]=page_structure
		control_structure['Application']=application_structure
	print page_list
	return render_template('home.html',page_list=page_list)

@app.route('/elements',methods= ['GET','POST'])
def getElements():
	page_name=request.form['pagename']
	print page_name
	tree=ET.parse('./controls/xml/{}.xml'.format(page_name))
	root=tree.getroot()
	page=root.find(page_name)
	element_list=[]
	for element in page:
		element_list.append(element.tag)
	print element_list
	#print render_template('modalbox.html',element_list=element_list)
	return render_template('modalbox.html',element_list=element_list)
@app.route('/elementinfo',methods= ['GET','POST'])
def getElementInfo():
	key_list=['using','value','parameterized','pattern']
	using_list=['xpath','id','className','cssSelector','name']
	page_name=request.form['pagename']
	element_name=request.form['elementname']
	print page_name,element_name
	tree=ET.parse('./controls/xml/{}.xml'.format(page_name))
	root=tree.getroot()
	page=root.find(page_name)
	element=page.find(element_name)
	print element.attrib
	return render_template('modalcontent.html',element_info=element.attrib,elementname=element_name,key_list=key_list,using_list=using_list)

if __name__ == '__main__':
    app.run(debug=True,host='0.0.0.0', port=8000)
