package com.pru.expandablelistex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import androidx.databinding.DataBindingUtil
import com.pru.expandablelistex.databinding.ListGroupChildBinding
import com.pru.expandablelistex.databinding.ListGroupHeaderBinding

class MenuExpandableAdapter(
    private val listHeaderData: List<DrawerMenuModel>,
    private val listChildData: HashMap<DrawerMenuModel, List<DrawerMenuModel>>
) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int = listHeaderData.size

    override fun getChildrenCount(groupPosition: Int): Int =
        listChildData[listHeaderData[groupPosition]]?.size ?: 0

    override fun getGroup(groupPosition: Int): DrawerMenuModel = listHeaderData[groupPosition]

    override fun getChild(groupPosition: Int, childPosition: Int): DrawerMenuModel? =
        listChildData[listHeaderData[groupPosition]]?.get(childPosition)

    override fun getGroupId(groupPosition: Int): Long = groupPosition.toLong()

    override fun getChildId(groupPosition: Int, childPosition: Int): Long = childPosition.toLong()

    override fun hasStableIds(): Boolean = false

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = true

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val binding: ListGroupHeaderBinding = if (convertView == null) {
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_group_header, parent, false
            )
        } else {
            DataBindingUtil.getBinding(convertView)!!
        }
        val item = getGroup(groupPosition)
        binding.tvItem.text = item.menuName

        return binding.root
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val binding: ListGroupChildBinding = if (convertView == null) {
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_group_child, parent, false
            )
        } else {
            DataBindingUtil.getBinding(convertView)!!
        }
        val item = getChild(groupPosition, childPosition)
        binding.tvItem.text = item?.menuName
        return binding.root
    }


}